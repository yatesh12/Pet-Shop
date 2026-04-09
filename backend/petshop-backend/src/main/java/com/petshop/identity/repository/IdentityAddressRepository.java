package com.petshop.identity.repository;

import com.petshop.identity.entity.IdentityAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityAddressRepository extends JpaRepository<IdentityAddress, Long> {
    List<IdentityAddress> findByUserIdOrderByDefaultAddressDescIdDesc(Long userId);
}
