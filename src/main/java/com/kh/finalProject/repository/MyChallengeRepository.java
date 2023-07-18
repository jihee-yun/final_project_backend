package com.kh.finalProject.repository;

import com.kh.finalProject.entity.MyChallenge;
import com.kh.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long>{
    Optional<MyChallenge> findById(Long id);
    List<MyChallenge> findByUser(User user);
}
