package com.petshop.web.service;

import com.petshop.shared.dto.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CommerceClient {

    @Qualifier("commerceRestClient")
    private final RestClient commerceRestClient;

    public CartDto cart(String ownerReference) {
        return commerceRestClient.get().uri("/api/commerce/carts/{ownerReference}", ownerReference).retrieve().body(CartDto.class);
    }

    public CartDto addToCart(String ownerReference, AddCartItemRequest request) {
        return commerceRestClient.post().uri("/api/commerce/carts/{ownerReference}/items", ownerReference).body(request).retrieve().body(CartDto.class);
    }

    public CartDto updateCartItem(String ownerReference, Long itemId, UpdateCartItemRequest request) {
        return commerceRestClient.put().uri("/api/commerce/carts/{ownerReference}/items/{itemId}", ownerReference, itemId).body(request).retrieve().body(CartDto.class);
    }

    public void removeCartItem(String ownerReference, Long itemId) {
        commerceRestClient.delete().uri("/api/commerce/carts/{ownerReference}/items/{itemId}", ownerReference, itemId).retrieve().toBodilessEntity();
    }

    public OrderDto checkout(CheckoutRequest request) {
        return commerceRestClient.post().uri("/api/commerce/orders/checkout").body(request).retrieve().body(OrderDto.class);
    }

    public List<OrderDto> ordersForUser(Long userId) {
        return commerceRestClient.get().uri("/api/commerce/orders/user/{userId}", userId).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<OrderDto> orders() {
        return commerceRestClient.get().uri("/api/commerce/orders").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public WishlistItemDto saveWishlist(WishlistRequest request) {
        return commerceRestClient.post().uri("/api/commerce/wishlist").body(request).retrieve().body(WishlistItemDto.class);
    }

    public List<WishlistItemDto> wishlist(Long userId) {
        return commerceRestClient.get().uri("/api/commerce/wishlist/user/{userId}", userId).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public void deleteWishlist(Long id) {
        commerceRestClient.delete().uri("/api/commerce/wishlist/{id}", id).retrieve().toBodilessEntity();
    }

    public InquiryDto inquire(InquiryRequest request) {
        return commerceRestClient.post().uri("/api/commerce/inquiries").body(request).retrieve().body(InquiryDto.class);
    }

    public ContactMessageDto contact(ContactMessageRequest request) {
        return commerceRestClient.post().uri("/api/commerce/contact-messages").body(request).retrieve().body(ContactMessageDto.class);
    }

    public ServiceBookingDto book(ServiceBookingRequest request) {
        return commerceRestClient.post().uri("/api/commerce/service-bookings").body(request).retrieve().body(ServiceBookingDto.class);
    }

    public List<ServiceBookingDto> bookingsForUser(Long userId) {
        return commerceRestClient.get().uri("/api/commerce/service-bookings/user/{userId}", userId).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public AdoptionRequestDto adopt(AdoptionRequestPayload request) {
        return commerceRestClient.post().uri("/api/commerce/adoption-requests").body(request).retrieve().body(AdoptionRequestDto.class);
    }

    public AdminAnalyticsDto analytics() {
        return commerceRestClient.get().uri("/api/commerce/admin/analytics").retrieve().body(AdminAnalyticsDto.class);
    }

    public List<InquiryDto> inquiries() {
        return commerceRestClient.get().uri("/api/commerce/admin/inquiries").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<ContactMessageDto> messages() {
        return commerceRestClient.get().uri("/api/commerce/admin/messages").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<ServiceBookingDto> bookings() {
        return commerceRestClient.get().uri("/api/commerce/admin/bookings").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<AdoptionRequestDto> adoptions() {
        return commerceRestClient.get().uri("/api/commerce/admin/adoptions").retrieve().body(new ParameterizedTypeReference<>() {});
    }
}
