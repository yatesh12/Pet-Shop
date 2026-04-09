package com.petshop.identity.repository;

import com.petshop.identity.entity.IdentityRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityRoleRepository extends JpaRepository<IdentityRole, Long> {
    Optional<IdentityRole> findByName(String name);
}
