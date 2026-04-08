package com.petshop.catalog.repository;

import com.petshop.catalog.entity.AdminTeamMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTeamMemberRepository extends JpaRepository<AdminTeamMember, Long> {
    List<AdminTeamMember> findByActiveTrueOrderByDisplayOrderAsc();
}
