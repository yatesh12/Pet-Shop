package com.petshop.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.shared.dto.ApiErrorResponse;
import com.petshop.web.dto.RegistrationForm;
import com.petshop.web.service.AuthClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;
    private final ObjectMapper objectMapper;

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
            authClient.register(form);
            redirectAttributes.addFlashAttribute("successMessage", "Your account has been created. You can now sign in.");
            return "redirect:/login";
        } catch (Exception ex) {
            bindingResult.rejectValue("email", "email.exists", resolveErrorMessage(ex));
            return "auth/register";
        }
    }

    private String resolveErrorMessage(Exception ex) {
        if (ex instanceof RestClientResponseException restEx) {
            try {
                ApiErrorResponse error = objectMapper.readValue(restEx.getResponseBodyAsString(), ApiErrorResponse.class);
                return error.message();
            } catch (Exception ignored) {
                return restEx.getStatusText();
            }
        }
        return ex.getMessage();
    }
}
