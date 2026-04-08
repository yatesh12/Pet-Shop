package com.petshop.commerce.repository;

import com.petshop.commerce.entity.CartItem;
import com.petshop.shared.enums.ItemType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndItemSlugAndItemType(Long cartId, String itemSlug, ItemType itemType);
}

