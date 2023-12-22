package com.example.bookstoreapplication.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ErrorControllerPages implements ErrorController {

    @RequestMapping("error")
    public String error(HttpServletRequest httpServletRequest, Model model) {
        Object errorStatusCode = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (errorStatusCode == null) {
            model.addAttribute("status", "Неизвестная ошибка");
            model.addAttribute("message", "Неизвестная ошибка");
            return "error-page";
        }
        int statusCode = Integer.parseInt(errorStatusCode.toString());
        String status;
        String message;
        switch (statusCode) {
            case 404 -> {
                status = "404 Not Found";
                message = "Страница не найдена";
            } case 400 -> {
                status = "400 Not Found";
                message = "Bad request";
            } case 500, 501, 502, 503, 504, 505 -> {
                status = "5xx Not Found";
                message = "Всё дело в слонах и выносливости черепах:(";
            } default -> {
                status = "Error page";
                message = "Всё дело в слонах и выносливости черепах:(";
            }
        }
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return "error-page";
    }

}
