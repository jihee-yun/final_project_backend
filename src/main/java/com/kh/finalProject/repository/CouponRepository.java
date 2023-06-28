package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByCouponName (String couponName);
}
