package com.petshop.catalog.repository;

import com.petshop.catalog.entity.CatalogServiceItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<CatalogServiceItem, Long> {
    Optional<CatalogServiceItem> findBySlug(String slug);
    List<CatalogServiceItem> findByActiveTrueOrderByFeaturedDescNameAsc();
}

