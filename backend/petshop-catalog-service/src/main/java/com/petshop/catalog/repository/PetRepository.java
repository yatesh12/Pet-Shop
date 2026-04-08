package com.petshop.catalog.repository;

import com.petshop.catalog.entity.Pet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
    Optional<Pet> findBySlug(String slug);
}

