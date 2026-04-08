package com.petshop.commerce.mapper;

import com.petshop.commerce.entity.*;
import com.petshop.shared.dto.*;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CommerceMapper {

    public CartDto toDto(Cart cart) {
        List<CartItemDto> items = cart.getItems().stream().map(this::toDto).toList();
        BigDecimal subtotal = items.stream().map(CartItemDto::lineTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        int totalItems = items.stream().mapToInt(CartItemDto::quantity).sum();
        return new CartDto(cart.getId(), cart.getOwnerReference(), items, subtotal, totalItems);
    }

    public CartItemDto toDto(CartItem item) {
        return new CartItemDto(item.getId(), item.getItemType(), item.getItemSlug(), item.getItemName(), item.getImageUrl(), item.getUnitPrice(), item.getQuantity(), item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
    }

    public OrderDto toDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getOwnerReference(),
                order.getUserId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getCustomerPhone(),
                order.getShippingAddress(),
                order.getCity(),
                order.getState(),
                order.getPostalCode(),
                order.getStatus(),
                order.getPaymentStatus(),
                order.getPaymentReference(),
                order.getSubtotal(),
                order.getDiscountAmount(),
                order.getTotalAmount(),
                order.getPromoCode(),
                order.getCreatedAt(),
                order.getItems().stream().map(this::toDto).toList()
        );
    }

    public OrderItemDto toDto(OrderItem item) {
        return new OrderItemDto(item.getId(), item.getItemType(), item.getItemSlug(), item.getItemName(), item.getImageUrl(), item.getUnitPrice(), item.getQuantity(), item.getLineTotal());
    }

    public WishlistItemDto toDto(WishlistItem item) {
        return new WishlistItemDto(item.getId(), item.getUserId(), item.getItemType(), item.getItemSlug(), item.getItemName(), item.getImageUrl(), item.getUnitPrice(), item.getCreatedAt());
    }

    public PaymentTransactionDto toDto(PaymentTransaction payment) {
        return new PaymentTransactionDto(payment.getId(), payment.getOrder().getId(), payment.getProvider(), payment.getProviderReference(), payment.getAmount(), payment.getCurrency(), payment.getStatus(), payment.getPaidAt());
    }

    public InquiryDto toDto(Inquiry inquiry) {
        return new InquiryDto(inquiry.getId(), inquiry.getUserId(), inquiry.getItemSlug(), inquiry.getItemType(), inquiry.getCustomerName(), inquiry.getCustomerEmail(), inquiry.getPhone(), inquiry.getMessage(), inquiry.getStatus(), inquiry.getCreatedAt());
    }

    public ServiceBookingDto toDto(ServiceBooking booking) {
        return new ServiceBookingDto(booking.getId(), booking.getUserId(), booking.getServiceSlug(), booking.getServiceName(), booking.getPetName(), booking.getPetType(), booking.getAppointmentDate(), booking.getNotes(), booking.getCustomerName(), booking.getCustomerEmail(), booking.getCustomerPhone(), booking.getStatus(), booking.getCreatedAt());
    }

    public AdoptionRequestDto toDto(AdoptionRequest request) {
        return new AdoptionRequestDto(request.getId(), request.getUserId(), request.getPetSlug(), request.getPetName(), request.getCustomerName(), request.getCustomerEmail(), request.getCustomerPhone(), request.getHomeType(), request.getExperienceLevel(), request.getNotes(), request.getStatus(), request.getCreatedAt());
    }

    public ContactMessageDto toDto(ContactMessage message) {
        return new ContactMessageDto(message.getId(), message.getUserId(), message.getType(), message.getName(), message.getEmail(), message.getSubject(), message.getMessage(), message.getStatus(), message.getCreatedAt());
    }
}
