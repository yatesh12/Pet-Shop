package com.petshop.identity.controller;

import com.petshop.identity.service.IdentityAuthService;
import com.petshop.shared.dto.AdminUserDto;
import com.petshop.shared.dto.UserRoleUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final IdentityAuthService identityAuthService;

    @GetMapping
    public List<AdminUserDto> users() {
        return identityAuthService.users();
    }

    @PutMapping("/{userId}/role")
    public AdminUserDto assignRole(@PathVariable Long userId, @Valid @RequestBody UserRoleUpdateRequest request) {
        return identityAuthService.assignRole(userId, request.roleName());
    }
}
