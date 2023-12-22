package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.form.UserRegistrationForm;
import com.example.bookstoreapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("registration")
    public String register(Model model) {
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        model.addAttribute("user", userRegistrationForm);
        return "/personal/registration";
    }

    @PostMapping("registration")
    public String register(UserRegistrationForm userRegistrationForm, RedirectAttributes redirectAttributes) {
        if (userService.registration(userRegistrationForm)) {
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так. Попробуйте снова.");
            return "redirect:/registration";
        }
    }
}
