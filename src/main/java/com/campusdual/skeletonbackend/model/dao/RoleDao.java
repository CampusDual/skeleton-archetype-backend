package com.campusdual.skeletonbackend.model.dao;

import com.campusdual.skeletonbackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
