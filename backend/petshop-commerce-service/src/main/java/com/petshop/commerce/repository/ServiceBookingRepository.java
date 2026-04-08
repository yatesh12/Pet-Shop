package com.petshop.commerce.repository;

import com.petshop.commerce.entity.ServiceBooking;
import com.petshop.shared.enums.BookingStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {
    List<ServiceBooking> findByUserIdOrderByCreatedAtDesc(Long userId);
    long countByStatus(BookingStatus status);
    List<ServiceBooking> findAllByOrderByCreatedAtDesc();
}

