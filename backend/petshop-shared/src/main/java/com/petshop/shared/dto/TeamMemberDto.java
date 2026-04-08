package com.petshop.shared.dto;

public record TeamMemberDto(
        Long id,
        String name,
        String roleTitle,
        String bio,
        String photoUrl,
        String email,
        String phone,
        Integer displayOrder,
        boolean active
) {
}

