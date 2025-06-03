package com.campusdual.skeletonbackend.model.dao;

import com.campusdual.skeletonbackend.model.User;
import com.campusdual.skeletonbackend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleDao extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);
}
