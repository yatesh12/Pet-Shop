package com.petshop.web.controller;

import com.petshop.shared.dto.*;
import com.petshop.shared.enums.ItemType;
import com.petshop.web.dto.AdoptionForm;
import com.petshop.web.dto.BookingForm;
import com.petshop.web.dto.ContactForm;
import com.petshop.web.dto.InquiryForm;
import com.petshop.web.service.CatalogClient;
import com.petshop.web.service.CommerceClient;
import com.petshop.web.service.CurrentUserService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CatalogPageController {

    private final CatalogClient catalogClient;
    private final CommerceClient commerceClient;
    private final CurrentUserService currentUserService;

    @GetMapping("/shop")
    public String shop(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        model.addAttribute("products", catalogClient.products(search, category, minPrice, maxPrice, available, page, 9, "name"));
        model.addAttribute("categories", catalogClient.productCategories());
        model.addAttribute("search", search);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("available", available);
        return "shop/index";
    }

    @PostMapping("/shop/cart")
    public String addProductToCart(@RequestParam String slug,
                                   @RequestParam String name,
                                   @RequestParam String imageUrl,
                                   @RequestParam BigDecimal price,
                                   RedirectAttributes redirectAttributes) {
        commerceClient.addToCart(currentUserService.ownerReference(), new AddCartItemRequest(ItemType.PRODUCT, slug, name, imageUrl, price, 1));
        redirectAttributes.addFlashAttribute("successMessage", "Item added to your cart.");
        return "redirect:/shop";
    }

    @GetMapping("/pets")
    public String pets(@RequestParam(required = false) String search,
                       @RequestParam(required = false) String category,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {
        model.addAttribute("pets", catalogClient.pets(search, category, page, 8));
        model.addAttribute("categories", catalogClient.petCategories());
        model.addAttribute("search", search);
        model.addAttribute("selectedCategory", category);
        return "pets/index";
    }

    @GetMapping("/pets/{slug}")
    public String petDetail(@PathVariable String slug, Model model) {
        model.addAttribute("pet", catalogClient.pet(slug));
        model.addAttribute("inquiryForm", new InquiryForm());
        model.addAttribute("adoptionForm", new AdoptionForm());
        return "pets/detail";
    }

    @PostMapping("/pets/{slug}/wishlist")
    public String savePet(@PathVariable String slug,
                          @RequestParam String name,
                          @RequestParam String imageUrl,
                          @RequestParam BigDecimal price,
                          RedirectAttributes redirectAttributes) {
        commerceClient.saveWishlist(new WishlistRequest(currentUserService.requireCurrentUserId(), ItemType.PET, slug, name, imageUrl, price));
        redirectAttributes.addFlashAttribute("successMessage", "Pet saved to your favorites.");
        return "redirect:/pets/" + slug;
    }

    @PostMapping("/pets/{slug}/inquiry")
    public String inquiry(@PathVariable String slug,
                          @RequestParam String petName,
                          @Valid @ModelAttribute("inquiryForm") InquiryForm form,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", catalogClient.pet(slug));
            model.addAttribute("adoptionForm", new AdoptionForm());
            return "pets/detail";
        }
        Long userId = currentUserService.getCurrentUser().map(UserProfileDto::id).orElse(null);
        commerceClient.inquire(new InquiryRequest(userId, slug, "PET", form.getCustomerName(), form.getCustomerEmail(), form.getPhone(), form.getMessage()));
        redirectAttributes.addFlashAttribute("successMessage", "Your inquiry has been sent.");
        return "redirect:/pets/" + slug;
    }

    @PostMapping("/pets/{slug}/adopt")
    public String adopt(@PathVariable String slug,
                        @RequestParam String petName,
                        @Valid @ModelAttribute("adoptionForm") AdoptionForm form,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", catalogClient.pet(slug));
            model.addAttribute("inquiryForm", new InquiryForm());
            return "pets/detail";
        }
        Long userId = currentUserService.getCurrentUser().map(UserProfileDto::id).orElse(null);
        commerceClient.adopt(new AdoptionRequestPayload(userId, slug, petName, form.getCustomerName(), form.getCustomerEmail(), form.getCustomerPhone(), form.getHomeType(), form.getExperienceLevel(), form.getNotes()));
        redirectAttributes.addFlashAttribute("successMessage", "Your adoption request has been submitted.");
        return "redirect:/pets/" + slug;
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("services", catalogClient.services());
        model.addAttribute("bookingForm", new BookingForm());
        return "services/index";
    }

    @PostMapping("/services/book")
    public String bookService(@Valid @ModelAttribute("bookingForm") BookingForm form,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("services", catalogClient.services());
            return "services/index";
        }
        Long userId = currentUserService.getCurrentUser().map(UserProfileDto::id).orElse(null);
        commerceClient.book(new ServiceBookingRequest(userId, form.getServiceSlug(), form.getServiceName(), form.getPetName(), form.getPetType(), form.getAppointmentDate(), form.getNotes(), form.getCustomerName(), form.getCustomerEmail(), form.getCustomerPhone()));
        redirectAttributes.addFlashAttribute("successMessage", "Booking request submitted. We’ll confirm shortly.");
        return "redirect:/services";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("team", catalogClient.team());
        return "content/about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "content/contact";
    }

    @PostMapping("/contact")
    public String contactSubmit(@Valid @ModelAttribute("contactForm") ContactForm form,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "content/contact";
        }
        Long userId = currentUserService.getCurrentUser().map(UserProfileDto::id).orElse(null);
        commerceClient.contact(new ContactMessageRequest(userId, form.getType(), form.getName(), form.getEmail(), form.getSubject(), form.getMessage()));
        redirectAttributes.addFlashAttribute("successMessage", "Your message has been sent.");
        return "redirect:/contact";
    }

    @GetMapping("/faq")
    public String faq(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("faqItems", catalogClient.faqs(search));
        model.addAttribute("search", search);
        return "content/faq";
    }

    @GetMapping("/blog")
    public String blog(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("posts", catalogClient.blog(page, 6));
        return "blog/index";
    }

    @GetMapping("/blog/{slug}")
    public String blogDetail(@PathVariable String slug, Model model) {
        model.addAttribute("post", catalogClient.blogPost(slug));
        return "blog/detail";
    }
}

