package com.kh.finalProject.service;

import com.kh.finalProject.dto.CouponDto;
import com.kh.finalProject.entity.Coupon;
import com.kh.finalProject.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {
    public final CouponRepository couponRepository;

    // 쿠폰 조회
    public List<CouponDto> selectCouponList() {
        List<Coupon> coupons = couponRepository.findAll();
        List<CouponDto> couponDtoList = new ArrayList<>();

        for(Coupon coupon : coupons) {
            CouponDto couponDto = new CouponDto();
            couponDto.setCouponName(coupon.getCouponName());
            couponDto.setPrice(coupon.getPrice());
            couponDtoList.add(couponDto);
        }
        return couponDtoList;
    }
}
