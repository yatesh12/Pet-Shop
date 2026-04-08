package com.petshop.catalog.repository;

import com.petshop.catalog.entity.Promotion;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findTop6ByActiveTrueAndStartsAtBeforeAndEndsAtAfterOrderByStartsAtDesc(LocalDateTime now, LocalDateTime later);
}

