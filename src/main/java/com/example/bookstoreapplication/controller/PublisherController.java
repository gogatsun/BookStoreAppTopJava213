package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.entity.Publisher;
import com.example.bookstoreapplication.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("")
    public String findAll(Model model) {
        List<Publisher> publishers = publisherService.findAll();
        model.addAttribute("publishers", publishers);
        return "publishers/publishers";
    }

    @GetMapping("new-publisher")
    public String getAddForm(Model model) {
        Publisher publisher = new Publisher();
        model.addAttribute("publisher", publisher);
        return "publishers/add-publisher-form";
    }

    @PostMapping("new-publisher")
    public String postAddForm(Publisher publisher, RedirectAttributes redirectAttributes) {
        Optional<Publisher> newPublisher = publisherService.add(publisher);
        if (newPublisher.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Издательство успешно добавлено");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
        }
        return "redirect:/publishers";
    }

    @GetMapping("update-publisher/{id}")
    public String getUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Publisher> updated = publisherService.findById(id);
        if (updated.isPresent()) {
            model.addAttribute("publisher", updated.get());
            return "publishers/update-publisher-form";
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Издателя с таким ID не найдено!");
        }
        return "redirect:/publishers";
    }

    @PostMapping("update-publisher")
    public String postUpdateForm(Publisher publisher, RedirectAttributes redirectAttributes) {
        Optional<Publisher> updated = publisherService.update(publisher);
        if (updated.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Изменения сохранены");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
        }
        return "redirect:/publishers";
    }

    @GetMapping("{id}")
    public String getBooksPublisher(@PathVariable Long id, Model model) {
        Optional<Publisher> publisher = publisherService.findById(id);
        if (publisher.isPresent()) {
            model.addAttribute("books", publisher.get().getBook());
            model.addAttribute("publisher", publisher.get());
            return "publishers/publisher-books";
        } else {
            return "redirect:/publishers";
        }
    }

}
