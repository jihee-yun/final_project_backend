package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByAdminIdAndPassword(String adminId, String password);
    boolean existsByAdminId(String adminId);
}
