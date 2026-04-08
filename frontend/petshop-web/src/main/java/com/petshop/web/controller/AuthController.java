package com.petshop.web.controller;

import com.petshop.web.dto.RegistrationForm;
import com.petshop.web.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountService userAccountService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("registrationForm") RegistrationForm form,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            userAccountService.register(form);
            redirectAttributes.addFlashAttribute("successMessage", "Your account has been created. You can now sign in.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("email", "email.exists", ex.getMessage());
            return "auth/register";
        }
    }
}
