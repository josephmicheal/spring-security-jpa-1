package com.javabrainio.springsecurityjpa.repository;

import com.javabrainio.springsecurityjpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Create a repository to connect the user
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
