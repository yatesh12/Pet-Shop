package com.petshop.commerce.entity;

import com.petshop.shared.enums.RequestStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "adoption_requests")
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "pet_slug", nullable = false, length = 120)
    private String petSlug;

    @Column(name = "pet_name", nullable = false, length = 120)
    private String petName;

    @Column(name = "customer_name", nullable = false, length = 120)
    private String customerName;

    @Column(name = "customer_email", nullable = false, length = 120)
    private String customerEmail;

    @Column(name = "customer_phone", nullable = false, length = 40)
    private String customerPhone;

    @Column(name = "home_type", nullable = false, length = 80)
    private String homeType;

    @Column(name = "experience_level", nullable = false, length = 80)
    private String experienceLevel;

    @Column(length = 2000)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RequestStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

