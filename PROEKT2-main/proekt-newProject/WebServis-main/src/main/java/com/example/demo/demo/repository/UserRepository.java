package com.example.demo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.demo.model.User;
import java.lang.String;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    // Custom query to find a user by username
   // User findByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Integer id);
    void save(UserDetails user);
}