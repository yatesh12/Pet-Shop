package com.petshop.commerce.controller;

import com.petshop.commerce.service.CommerceService;
import com.petshop.shared.dto.*;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commerce")
@RequiredArgsConstructor
public class CommerceController {

    private final CommerceService commerceService;

    @GetMapping("/carts/{ownerReference}")
    public CartDto cart(@PathVariable String ownerReference) {
        return commerceService.getCart(ownerReference);
    }

    @PostMapping("/carts/{ownerReference}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto addCartItem(@PathVariable String ownerReference, @Valid @RequestBody AddCartItemRequest request) {
        return commerceService.addCartItem(ownerReference, request);
    }

    @PutMapping("/carts/{ownerReference}/items/{itemId}")
    public CartDto updateCartItem(@PathVariable String ownerReference, @PathVariable Long itemId, @Valid @RequestBody UpdateCartItemRequest request) {
        return commerceService.updateCartItem(ownerReference, itemId, request);
    }

    @DeleteMapping("/carts/{ownerReference}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCartItem(@PathVariable String ownerReference, @PathVariable Long itemId) {
        commerceService.removeCartItem(ownerReference, itemId);
    }

    @PostMapping("/orders/checkout")
    public OrderDto checkout(@Valid @RequestBody CheckoutRequest request) {
        return commerceService.checkout(request);
    }

    @GetMapping("/orders/user/{userId}")
    public List<OrderDto> ordersForUser(@PathVariable Long userId) {
        return commerceService.getOrdersForUser(userId);
    }

    @GetMapping("/orders")
    public List<OrderDto> orders() {
        return commerceService.getAllOrders();
    }

    @PostMapping("/wishlist")
    @ResponseStatus(HttpStatus.CREATED)
    public WishlistItemDto addWishlist(@Valid @RequestBody WishlistRequest request) {
        return commerceService.saveWishlistItem(request);
    }

    @GetMapping("/wishlist/user/{userId}")
    public List<WishlistItemDto> wishlist(@PathVariable Long userId) {
        return commerceService.getWishlist(userId);
    }

    @DeleteMapping("/wishlist/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWishlist(@PathVariable Long id) {
        commerceService.deleteWishlistItem(id);
    }

    @PostMapping("/inquiries")
    @ResponseStatus(HttpStatus.CREATED)
    public InquiryDto inquiry(@Valid @RequestBody InquiryRequest request) {
        return commerceService.saveInquiry(request);
    }

    @PostMapping("/contact-messages")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactMessageDto contact(@Valid @RequestBody ContactMessageRequest request) {
        return commerceService.saveContactMessage(request);
    }

    @PostMapping("/service-bookings")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceBookingDto booking(@Valid @RequestBody ServiceBookingRequest request) {
        return commerceService.saveBooking(request);
    }

    @GetMapping("/service-bookings/user/{userId}")
    public List<ServiceBookingDto> bookingsForUser(@PathVariable Long userId) {
        return commerceService.getBookingsForUser(userId);
    }

    @PostMapping("/adoption-requests")
    @ResponseStatus(HttpStatus.CREATED)
    public AdoptionRequestDto adoption(@Valid @RequestBody AdoptionRequestPayload request) {
        return commerceService.saveAdoptionRequest(request);
    }

    @GetMapping("/admin/analytics")
    public AdminAnalyticsDto analytics() {
        return commerceService.getAnalytics();
    }

    @GetMapping("/admin/inquiries")
    public List<InquiryDto> inquiries() {
        return commerceService.getInquiries();
    }

    @GetMapping("/admin/messages")
    public List<ContactMessageDto> messages() {
        return commerceService.getContactMessages();
    }

    @GetMapping("/admin/bookings")
    public List<ServiceBookingDto> bookings() {
        return commerceService.getBookings();
    }

    @GetMapping("/admin/adoptions")
    public List<AdoptionRequestDto> adoptions() {
        return commerceService.getAdoptionRequests();
    }
}

