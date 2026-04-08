package com.petshop.catalog.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "services")
public class CatalogServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(nullable = false, length = 60)
    private String category;

    @Column(name = "short_description", nullable = false, length = 255)
    private String shortDescription;

    @Column(nullable = false, length = 1400)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private boolean featured;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "image_url", length = 255)
    private String imageUrl;
}

