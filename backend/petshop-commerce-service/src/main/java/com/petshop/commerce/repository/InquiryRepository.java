package com.petshop.commerce.repository;

import com.petshop.commerce.entity.Inquiry;
import com.petshop.shared.enums.RequestStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    long countByStatus(RequestStatus status);
    List<Inquiry> findAllByOrderByCreatedAtDesc();
}

