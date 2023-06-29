package com.kh.finalProject.repository;

import com.kh.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserIdAndPassword(String userId, String password);
    boolean existsByEmail(String email);
}
