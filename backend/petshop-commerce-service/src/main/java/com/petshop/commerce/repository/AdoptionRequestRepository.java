package com.petshop.commerce.repository;

import com.petshop.commerce.entity.AdoptionRequest;
import com.petshop.shared.enums.RequestStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    long countByStatus(RequestStatus status);
    List<AdoptionRequest> findAllByOrderByCreatedAtDesc();
}

