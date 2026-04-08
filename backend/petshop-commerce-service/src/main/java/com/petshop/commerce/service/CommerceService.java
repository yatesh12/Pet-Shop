package com.petshop.commerce.service;

import com.petshop.commerce.entity.*;
import com.petshop.commerce.exception.BusinessException;
import com.petshop.commerce.exception.ResourceNotFoundException;
import com.petshop.commerce.mapper.CommerceMapper;
import com.petshop.commerce.repository.*;
import com.petshop.shared.dto.*;
import com.petshop.shared.enums.BookingStatus;
import com.petshop.shared.enums.PaymentStatus;
import com.petshop.shared.enums.RequestStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommerceService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final OrderRepository orderRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final WishlistRepository wishlistRepository;
    private final InquiryRepository inquiryRepository;
    private final ServiceBookingRepository serviceBookingRepository;
    private final AdoptionRequestRepository adoptionRequestRepository;
    private final ContactMessageRepository contactMessageRepository;
    private final CommerceMapper mapper;
    private final PaymentProvider paymentProvider;

    public CartDto getCart(String ownerReference) {
        Cart cart = cartRepository.findByOwnerReference(ownerReference).orElseGet(() -> {
            Cart created = new Cart();
            created.setOwnerReference(ownerReference);
            return cartRepository.save(created);
        });
        return mapper.toDto(cart);
    }

    public CartDto addCartItem(String ownerReference, AddCartItemRequest request) {
        Cart cart = cartRepository.findByOwnerReference(ownerReference).orElseGet(() -> {
            Cart created = new Cart();
            created.setOwnerReference(ownerReference);
            return cartRepository.save(created);
        });

        CartItem item = cartItemRepository.findByCartIdAndItemSlugAndItemType(cart.getId(), request.itemSlug(), request.itemType())
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setItemSlug(request.itemSlug());
                    newItem.setItemType(request.itemType());
                    newItem.setItemName(request.itemName());
                    newItem.setImageUrl(request.imageUrl());
                    newItem.setUnitPrice(request.unitPrice());
                    newItem.setQuantity(0);
                    cart.getItems().add(newItem);
                    return newItem;
                });
        item.setQuantity(item.getQuantity() + request.quantity());
        cartItemRepository.save(item);
        return mapper.toDto(cartRepository.findById(cart.getId()).orElseThrow());
    }

    public CartDto updateCartItem(String ownerReference, Long itemId, UpdateCartItemRequest request) {
        Cart cart = cartRepository.findByOwnerReference(ownerReference).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new BusinessException("Cart item does not belong to the current cart");
        }
        item.setQuantity(request.quantity());
        return mapper.toDto(cart);
    }

    public void removeCartItem(String ownerReference, Long itemId) {
        Cart cart = cartRepository.findByOwnerReference(ownerReference).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new BusinessException("Cart item does not belong to the current cart");
        }
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
    }

    public OrderDto checkout(CheckoutRequest request) {
        if (!Boolean.TRUE.equals(request.agreeToTerms())) {
            throw new BusinessException("You must agree to the terms to complete checkout");
        }
        Cart cart = cartRepository.findByOwnerReference(request.ownerReference())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        if (cart.getItems().isEmpty()) {
            throw new BusinessException("Your cart is empty");
        }

        BigDecimal subtotal = cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discount = resolveDiscount(request.promoCode(), subtotal);
        BigDecimal total = subtotal.subtract(discount);

        Order order = new Order();
        order.setOwnerReference(request.ownerReference());
        order.setUserId(request.userId());
        order.setCustomerName(request.customerName());
        order.setCustomerEmail(request.customerEmail());
        order.setCustomerPhone(request.customerPhone());
        order.setShippingAddress(request.shippingAddress());
        order.setCity(request.city());
        order.setState(request.state());
        order.setPostalCode(request.postalCode());
        order.setNotes(request.notes());
        order.setStatus(com.petshop.shared.enums.OrderStatus.CONFIRMED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setSubtotal(subtotal);
        order.setDiscountAmount(discount);
        order.setTotalAmount(total);
        order.setPromoCode(request.promoCode());

        cart.getItems().forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItemType(cartItem.getItemType());
            orderItem.setItemSlug(cartItem.getItemSlug());
            orderItem.setItemName(cartItem.getItemName());
            orderItem.setImageUrl(cartItem.getImageUrl());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setLineTotal(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            order.getItems().add(orderItem);
        });

        order = orderRepository.save(order);
        PaymentResult paymentResult = paymentProvider.charge(total, request.paymentMethod(), "order-" + order.getId());
        order.setPaymentStatus(paymentResult.status());
        order.setPaymentReference(paymentResult.providerReference());

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setOrder(order);
        transaction.setProvider(paymentResult.provider());
        transaction.setProviderReference(paymentResult.providerReference());
        transaction.setAmount(total);
        transaction.setCurrency("USD");
        transaction.setStatus(paymentResult.status());
        transaction.setPaidAt(LocalDateTime.now());
        transaction.setRawResponse(paymentResult.rawResponse());
        paymentTransactionRepository.save(transaction);

        cart.getItems().clear();
        return mapper.toDto(order);
    }

    private BigDecimal resolveDiscount(String promoCodeValue, BigDecimal subtotal) {
        if (promoCodeValue == null || promoCodeValue.isBlank()) {
            return BigDecimal.ZERO;
        }
        PromoCode promoCode = promoCodeRepository.findByCodeIgnoreCase(promoCodeValue)
                .filter(PromoCode::isActive)
                .filter(code -> code.getExpiresAt() == null || code.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new BusinessException("Invalid or expired promo code"));
        if ("PERCENT".equalsIgnoreCase(promoCode.getDiscountType())) {
            return subtotal.multiply(promoCode.getDiscountValue()).divide(BigDecimal.valueOf(100));
        }
        return promoCode.getDiscountValue();
    }

    @Transactional(readOnly = true)
    public java.util.List<OrderDto> getOrdersForUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public java.util.List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(mapper::toDto).toList();
    }

    public WishlistItemDto saveWishlistItem(WishlistRequest request) {
        WishlistItem item = wishlistRepository.findByUserIdAndItemSlugAndItemType(request.userId(), request.itemSlug(), request.itemType())
                .orElseGet(WishlistItem::new);
        item.setUserId(request.userId());
        item.setItemType(request.itemType());
        item.setItemSlug(request.itemSlug());
        item.setItemName(request.itemName());
        item.setImageUrl(request.imageUrl());
        item.setUnitPrice(request.unitPrice());
        return mapper.toDto(wishlistRepository.save(item));
    }

    @Transactional(readOnly = true)
    public java.util.List<WishlistItemDto> getWishlist(Long userId) {
        return wishlistRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(mapper::toDto).toList();
    }

    public void deleteWishlistItem(Long id) {
        wishlistRepository.deleteById(id);
    }

    public InquiryDto saveInquiry(InquiryRequest request) {
        Inquiry inquiry = new Inquiry();
        inquiry.setUserId(request.userId());
        inquiry.setItemSlug(request.itemSlug());
        inquiry.setItemType(request.itemType());
        inquiry.setCustomerName(request.customerName());
        inquiry.setCustomerEmail(request.customerEmail());
        inquiry.setPhone(request.phone());
        inquiry.setMessage(request.message());
        inquiry.setStatus(RequestStatus.NEW);
        return mapper.toDto(inquiryRepository.save(inquiry));
    }

    public ContactMessageDto saveContactMessage(ContactMessageRequest request) {
        ContactMessage message = new ContactMessage();
        message.setUserId(request.userId());
        message.setType(request.type());
        message.setName(request.name());
        message.setEmail(request.email());
        message.setSubject(request.subject());
        message.setMessage(request.message());
        message.setStatus(RequestStatus.NEW);
        return mapper.toDto(contactMessageRepository.save(message));
    }

    public ServiceBookingDto saveBooking(ServiceBookingRequest request) {
        ServiceBooking booking = new ServiceBooking();
        booking.setUserId(request.userId());
        booking.setServiceSlug(request.serviceSlug());
        booking.setServiceName(request.serviceName());
        booking.setPetName(request.petName());
        booking.setPetType(request.petType());
        booking.setAppointmentDate(request.appointmentDate());
        booking.setNotes(request.notes());
        booking.setCustomerName(request.customerName());
        booking.setCustomerEmail(request.customerEmail());
        booking.setCustomerPhone(request.customerPhone());
        booking.setStatus(BookingStatus.REQUESTED);
        return mapper.toDto(serviceBookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public java.util.List<ServiceBookingDto> getBookingsForUser(Long userId) {
        return serviceBookingRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(mapper::toDto).toList();
    }

    public AdoptionRequestDto saveAdoptionRequest(AdoptionRequestPayload request) {
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setUserId(request.userId());
        adoptionRequest.setPetSlug(request.petSlug());
        adoptionRequest.setPetName(request.petName());
        adoptionRequest.setCustomerName(request.customerName());
        adoptionRequest.setCustomerEmail(request.customerEmail());
        adoptionRequest.setCustomerPhone(request.customerPhone());
        adoptionRequest.setHomeType(request.homeType());
        adoptionRequest.setExperienceLevel(request.experienceLevel());
        adoptionRequest.setNotes(request.notes());
        adoptionRequest.setStatus(RequestStatus.NEW);
        return mapper.toDto(adoptionRequestRepository.save(adoptionRequest));
    }

    @Transactional(readOnly = true)
    public java.util.List<InquiryDto> getInquiries() {
        return inquiryRepository.findAllByOrderByCreatedAtDesc().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public java.util.List<ContactMessageDto> getContactMessages() {
        return contactMessageRepository.findAllByOrderByCreatedAtDesc().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public java.util.List<ServiceBookingDto> getBookings() {
        return serviceBookingRepository.findAllByOrderByCreatedAtDesc().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public java.util.List<AdoptionRequestDto> getAdoptionRequests() {
        return adoptionRequestRepository.findAllByOrderByCreatedAtDesc().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AdminAnalyticsDto getAnalytics() {
        BigDecimal revenue = orderRepository.findAll().stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new AdminAnalyticsDto(
                inquiryRepository.countByStatus(RequestStatus.NEW),
                serviceBookingRepository.countByStatus(BookingStatus.REQUESTED),
                adoptionRequestRepository.countByStatus(RequestStatus.NEW),
                orderRepository.count(),
                revenue,
                wishlistRepository.count()
        );
    }
}
