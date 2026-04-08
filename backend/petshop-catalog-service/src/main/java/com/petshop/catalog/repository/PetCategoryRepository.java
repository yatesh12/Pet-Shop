package com.petshop.catalog.repository;

import com.petshop.catalog.entity.PetCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetCategoryRepository extends JpaRepository<PetCategory, Long> {
    Optional<PetCategory> findBySlug(String slug);
}

