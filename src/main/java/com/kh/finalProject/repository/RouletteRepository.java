package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Roulette;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RouletteRepository extends JpaRepository<Roulette, Long> {
    Roulette findByMember(Member member);
    List<Roulette> findByMemberOrderByLastSpinTimeDesc(Member member);

//    Optional<Roulette> findByMemberAndLastSpinTime(Member member, Date LastSpinTime);


}
