package com.petshop.identity.repository;

import com.petshop.identity.entity.IdentityUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityUserRepository extends JpaRepository<IdentityUser, Long> {
    Optional<IdentityUser> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
