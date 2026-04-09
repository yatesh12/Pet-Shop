package com.petshop.web.controller;

import com.petshop.web.dto.AddressForm;
import com.petshop.web.dto.ProfileForm;
import com.petshop.shared.dto.UserProfileDto;
import com.petshop.web.service.AuthClient;
import com.petshop.web.service.CommerceClient;
import com.petshop.web.service.CurrentUserService;
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
public class AccountController {

    private final CurrentUserService currentUserService;
    private final AuthClient authClient;
    private final CommerceClient commerceClient;

    @GetMapping("/account")
    public String account(Model model) {
        UserProfileDto user = authClient.currentUser();
        ProfileForm profileForm = new ProfileForm();
        profileForm.setFirstName(user.firstName());
        profileForm.setLastName(user.lastName());
        profileForm.setEmail(user.email());
        profileForm.setPhone(user.phone());
        model.addAttribute("profileForm", profileForm);
        model.addAttribute("addressForm", new AddressForm());
        model.addAttribute("user", user);
        model.addAttribute("addresses", authClient.addresses());
        model.addAttribute("orders", commerceClient.ordersForUser(user.id()));
        model.addAttribute("wishlist", commerceClient.wishlist(user.id()));
        model.addAttribute("bookings", commerceClient.bookingsForUser(user.id()));
        return "account/index";
    }

    @PostMapping("/account/profile")
    public String updateProfile(@Valid @ModelAttribute("profileForm") ProfileForm form,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return account(model);
        }
        authClient.updateProfile(form);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated.");
        return "redirect:/account";
    }

    @PostMapping("/account/address")
    public String saveAddress(@Valid @ModelAttribute("addressForm") AddressForm form,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return account(model);
        }
        authClient.saveAddress(form);
        redirectAttributes.addFlashAttribute("successMessage", "Address saved.");
        return "redirect:/account";
    }
}

