package com.petshop.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admin_team_members")
public class AdminTeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "role_title", nullable = false, length = 120)
    private String roleTitle;

    @Column(nullable = false, length = 1200)
    private String bio;

    @Column(name = "photo_url", length = 255)
    private String photoUrl;

    @Column(length = 120)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(nullable = false)
    private boolean active;
}
