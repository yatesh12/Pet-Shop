package com.petshop.commerce.repository;

import com.petshop.commerce.entity.PromoCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    Optional<PromoCode> findByCodeIgnoreCase(String code);
}

