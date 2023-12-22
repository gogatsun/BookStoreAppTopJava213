package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.entity.Series;
import com.example.bookstoreapplication.service.SeriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping("")
    public String findAll(Model model) {
        List<Series> series = seriesService.findAll();
        model.addAttribute("series", series);
        return "series/series";
    }

    @GetMapping("new-series")
    public String getAddForm(Model model) {
        Series series = new Series();
        model.addAttribute("series", series);
        return "series/add-series-form";
    }

    @PostMapping("new-series")
    public String postAddForm(Series series, RedirectAttributes redirectAttributes) {
        Optional<Series> existing = seriesService.add(series);
        if (existing.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Добавление выполнено успешно!");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
        }
        return "redirect:/series";
    }

    @GetMapping("update-series/{id}")
    public String getUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Series> series = seriesService.findById(id);
        if (series.isEmpty()) {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
            return "redirect:/series";
        } else {
            model.addAttribute("series", series.get());
        }
        return "series/update-series-form";
    }

    @PostMapping("update-series")
    public String postUpdateForm(Series series, RedirectAttributes redirectAttributes) {
        Optional<Series> updated = seriesService.update(series);
        if (updated.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Изменения сохранены");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
        }
        return "redirect:/series";
    }

    @GetMapping("{id}")
    public String getSeriesBooks(@PathVariable Long id, Model model) {
        Optional<Series> series = seriesService.findById(id);
        if (series.isPresent()) {
            model.addAttribute("series", series.get());
            model.addAttribute("books", series.get().getBook());
            return "series/series-books";
        } else {
            return "redirect:/series";
        }
    }
}
