package com.petshop.commerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.petshop.commerce.entity.Cart;
import com.petshop.commerce.mapper.CommerceMapper;
import com.petshop.commerce.repository.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommerceServiceTest {

    @Mock private CartRepository cartRepository;
    @Mock private CartItemRepository cartItemRepository;
    @Mock private PromoCodeRepository promoCodeRepository;
    @Mock private OrderRepository orderRepository;
    @Mock private PaymentTransactionRepository paymentTransactionRepository;
    @Mock private WishlistRepository wishlistRepository;
    @Mock private InquiryRepository inquiryRepository;
    @Mock private ServiceBookingRepository serviceBookingRepository;
    @Mock private AdoptionRequestRepository adoptionRequestRepository;
    @Mock private ContactMessageRepository contactMessageRepository;
    @Mock private PaymentProvider paymentProvider;

    @Test
    void shouldCreateCartIfMissing() {
        CommerceService commerceService = new CommerceService(
                cartRepository,
                cartItemRepository,
                promoCodeRepository,
                orderRepository,
                paymentTransactionRepository,
                wishlistRepository,
                inquiryRepository,
                serviceBookingRepository,
                adoptionRequestRepository,
                contactMessageRepository,
                new CommerceMapper(),
                paymentProvider
        );
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setOwnerReference("user-2");
        when(cartRepository.findByOwnerReference("user-2")).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        var response = commerceService.getCart("user-2");

        assertThat(response.ownerReference()).isEqualTo("user-2");
    }
}
