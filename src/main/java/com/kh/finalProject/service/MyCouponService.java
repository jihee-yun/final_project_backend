package com.kh.finalProject.service;

import com.kh.finalProject.dto.MyCouponDto;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.MyCoupon;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.MyCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyCouponService {
    public final MyCouponRepository myCouponRepository;
    public final MemberRepository memberRepository;

    // 내 쿠폰 조회
    public List<MyCouponDto> myCouponList(Long memberNum, Long CouponId) {
        Optional<Member> member = memberRepository.findByMemberNum(memberNum);
        List<MyCouponDto> myCouponDtos = new ArrayList<>();
        if (member.isPresent()) {
            List<MyCoupon> myCoupons = myCouponRepository.findByMember(member.get());
            for (MyCoupon myCoupon : myCoupons) {
                MyCouponDto myCouponDto = new MyCouponDto();
                myCouponDto.setId(myCoupon.getId());
                myCouponDto.setMemberNum(member.get().getMemberNum());
                myCouponDto.setCouponId(myCoupon.getCoupon().getId());
                myCouponDto.setCouponName(myCoupon.getCoupon().getCouponName());
                myCouponDtos.add(myCouponDto);
            }
        }
        return myCouponDtos;
    }
}
