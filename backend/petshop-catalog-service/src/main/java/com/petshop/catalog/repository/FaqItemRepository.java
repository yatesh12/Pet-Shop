package com.petshop.catalog.repository;

import com.petshop.catalog.entity.FaqItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqItemRepository extends JpaRepository<FaqItem, Long> {
    List<FaqItem> findByActiveTrueAndQuestionContainingIgnoreCaseOrderByDisplayOrderAsc(String search);
    List<FaqItem> findByActiveTrueOrderByDisplayOrderAsc();
}

