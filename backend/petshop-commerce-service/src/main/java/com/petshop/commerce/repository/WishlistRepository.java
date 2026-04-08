package com.petshop.commerce.repository;

import com.petshop.commerce.entity.WishlistItem;
import com.petshop.shared.enums.ItemType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<WishlistItem> findByUserIdAndItemSlugAndItemType(Long userId, String itemSlug, ItemType itemType);
}

