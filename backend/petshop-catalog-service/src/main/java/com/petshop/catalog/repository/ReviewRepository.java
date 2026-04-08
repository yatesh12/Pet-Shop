package com.petshop.catalog.repository;

import com.petshop.catalog.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findTop6ByApprovedTrueOrderByCreatedAtDesc();
}

