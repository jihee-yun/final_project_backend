package com.kh.finalProject.repository;

import com.kh.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserIdAndPassword(String userId, String password);
//    Optional<User> findByNameAndPhoneAndEmail(String name, String phone, String email);
//    Optional<User> findByNameAndEmail(String name, String email);
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);

    // 유저 번호로 조회
    Optional<User> findByUserNum(Long userNum);
}
