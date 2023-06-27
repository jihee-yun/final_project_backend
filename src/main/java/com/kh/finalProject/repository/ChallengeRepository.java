package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByChallengeName (String challengeName);
}
