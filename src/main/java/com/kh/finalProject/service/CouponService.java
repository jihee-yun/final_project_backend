package com.kh.finalProject.service;

import com.kh.finalProject.dto.CouponDto;
import com.kh.finalProject.entity.Coupon;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.repository.CouponRepository;
import com.kh.finalProject.repository.MemberRepository;
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
    public final MemberRepository memberRepository;

    // 쿠폰 조회
    public List<CouponDto> selectCouponList() {
        List<Coupon> coupons = couponRepository.findAll();
        List<CouponDto> couponDtoList = new ArrayList<>();

        for(Coupon coupon : coupons) {
            CouponDto couponDto = new CouponDto();
            couponDto.setId(coupon.getId());
            couponDto.setCouponName(coupon.getCouponName());
            couponDto.setPrice(coupon.getPrice());
            couponDto.setId(coupon.getId());
            couponDtoList.add(couponDto);
        }
        return couponDtoList;
    }
    // 쿠폰 결제
    public String couponPayment (Long memberNum, Long couponId) {
        Member member = memberRepository.findByMemberNum(memberNum).orElse(null);
        Coupon coupon = couponRepository.findById(couponId).orElse(null);
        int myPoint = member.getTotalPoint();
        int couponPrice = coupon.getPrice();

        if(myPoint >= couponPrice) {
            member.setTotalPoint(myPoint - couponPrice);
            memberRepository.save(member);
            return "결제 완료";
        } else {
            return "포인트 부족으로 결제 불가";
        }
    }
}
