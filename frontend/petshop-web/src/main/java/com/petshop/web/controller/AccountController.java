package com.petshop.web.controller;

import com.petshop.web.dto.AddressForm;
import com.petshop.web.dto.ProfileForm;
import com.petshop.web.entity.AppUser;
import com.petshop.web.service.CommerceClient;
import com.petshop.web.service.CurrentUserService;
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
public class AccountController {

    private final CurrentUserService currentUserService;
    private final UserAccountService userAccountService;
    private final CommerceClient commerceClient;

    @GetMapping("/account")
    public String account(Model model) {
        AppUser user = currentUserService.getCurrentUser().orElseThrow();
        ProfileForm profileForm = new ProfileForm();
        profileForm.setFirstName(user.getFirstName());
        profileForm.setLastName(user.getLastName());
        profileForm.setEmail(user.getEmail());
        profileForm.setPhone(user.getPhone());
        model.addAttribute("profileForm", profileForm);
        model.addAttribute("addressForm", new AddressForm());
        model.addAttribute("user", user);
        model.addAttribute("addresses", userAccountService.addresses(user.getId()));
        model.addAttribute("orders", commerceClient.ordersForUser(user.getId()));
        model.addAttribute("wishlist", commerceClient.wishlist(user.getId()));
        model.addAttribute("bookings", commerceClient.bookingsForUser(user.getId()));
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
        userAccountService.updateProfile(currentUserService.requireCurrentUserId(), form);
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
        userAccountService.saveAddress(currentUserService.requireCurrentUserId(), form);
        redirectAttributes.addFlashAttribute("successMessage", "Address saved.");
        return "redirect:/account";
    }
}

