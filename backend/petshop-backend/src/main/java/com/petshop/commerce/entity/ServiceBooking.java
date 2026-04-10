package com.petshop.commerce.entity;

import com.petshop.shared.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "service_bookings")
public class ServiceBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "service_slug", nullable = false, length = 120)
    private String serviceSlug;

    @Column(name = "service_name", nullable = false, length = 120)
    private String serviceName;

    @Column(name = "pet_name", nullable = false, length = 120)
    private String petName;

    @Column(name = "pet_type", nullable = false, length = 80)
    private String petType;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(length = 2000)
    private String notes;

    @Column(name = "customer_name", nullable = false, length = 120)
    private String customerName;

    @Column(name = "customer_email", nullable = false, length = 120)
    private String customerEmail;

    @Column(name = "customer_phone", nullable = false, length = 40)
    private String customerPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
