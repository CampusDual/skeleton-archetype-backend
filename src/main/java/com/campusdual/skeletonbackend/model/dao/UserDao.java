package com.campusdual.skeletonbackend.model.dao;

import com.campusdual.skeletonbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);
}
