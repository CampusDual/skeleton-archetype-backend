package com.campusdual.skeletonbackend.model.dao;

import com.campusdual.skeletonbackend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleDao extends JpaRepository<UserRole, Long> {

}
