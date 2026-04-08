package com.petshop.catalog.entity;

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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviewer_name", nullable = false, length = 100)
    private String reviewerName;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 1200)
    private String body;

    @Column(name = "subject_type", nullable = false, length = 20)
    private String subjectType;

    @Column(name = "subject_slug", nullable = false, length = 120)
    private String subjectSlug;

    @Column(nullable = false)
    private boolean approved;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

