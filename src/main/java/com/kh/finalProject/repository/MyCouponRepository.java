package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.MyCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyCouponRepository extends JpaRepository<MyCoupon, Long> {
    List<MyCoupon> findByMember(Member member);

}
