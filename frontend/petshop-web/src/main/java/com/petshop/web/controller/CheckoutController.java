package com.petshop.web.controller;

import com.petshop.shared.dto.CheckoutRequest;
import com.petshop.shared.dto.OrderDto;
import com.petshop.shared.dto.UpdateCartItemRequest;
import com.petshop.web.dto.CheckoutForm;
import com.petshop.web.service.CommerceClient;
import com.petshop.web.service.CurrentUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final CurrentUserService currentUserService;
    private final CommerceClient commerceClient;

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cart", commerceClient.cart(currentUserService.ownerReference()));
        return "cart/index";
    }

    @PostMapping("/cart/items/{itemId}")
    public String updateCart(@PathVariable Long itemId, Integer quantity, RedirectAttributes redirectAttributes) {
        commerceClient.updateCartItem(currentUserService.ownerReference(), itemId, new UpdateCartItemRequest(quantity));
        redirectAttributes.addFlashAttribute("successMessage", "Cart updated.");
        return "redirect:/cart";
    }

    @PostMapping("/cart/items/{itemId}/remove")
    public String removeCart(@PathVariable Long itemId, RedirectAttributes redirectAttributes) {
        commerceClient.removeCartItem(currentUserService.ownerReference(), itemId);
        redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart.");
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("cart", commerceClient.cart(currentUserService.ownerReference()));
        model.addAttribute("checkoutForm", new CheckoutForm());
        return "cart/checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@Valid @ModelAttribute("checkoutForm") CheckoutForm form,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cart", commerceClient.cart(currentUserService.ownerReference()));
            return "cart/checkout";
        }
        OrderDto order = commerceClient.checkout(new CheckoutRequest(
                currentUserService.ownerReference(),
                currentUserService.requireCurrentUserId(),
                form.getCustomerName(),
                form.getCustomerEmail(),
                form.getCustomerPhone(),
                form.getShippingAddress(),
                form.getCity(),
                form.getState(),
                form.getPostalCode(),
                form.getNotes(),
                form.getPromoCode(),
                form.getPaymentMethod(),
                form.getAgreeToTerms()
        ));
        model.addAttribute("order", order);
        return "cart/confirmation";
    }
}

