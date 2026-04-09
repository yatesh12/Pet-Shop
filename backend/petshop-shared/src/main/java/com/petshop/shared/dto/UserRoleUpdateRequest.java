package com.petshop.shared.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRoleUpdateRequest(
        @NotBlank String roleName
) {
}
