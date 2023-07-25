package com.kh.finalProject.service;

import com.kh.finalProject.dto.CouponDto;
import com.kh.finalProject.entity.Coupon;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Point;
import com.kh.finalProject.repository.CouponRepository;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {
    public final CouponRepository couponRepository;
    public final MemberRepository memberRepository;
    public final PointRepository pointRepository;

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
    public boolean couponPayment (Long memberNum, Long couponId) {
        Member member = memberRepository.findByMemberNum(memberNum).orElse(null);
        Coupon coupon = couponRepository.findById(couponId).orElse(null);
        int myPoint = member.getTotalPoint();
        int couponPrice = coupon.getPrice();

        if(myPoint >= couponPrice) {
            member.setTotalPoint(myPoint - couponPrice);

            Point point = new Point();
            point.setPoint(-couponPrice);
            point.setPointType("coupon");
            point.setMember(member);
            point.setPointDate(LocalDate.now());
            pointRepository.save(point);

            memberRepository.save(member);
            return true;
        } else {
            return false;
        }
    }
}
