package com.kh.finalProject.service;

import com.kh.finalProject.dto.PointDto;
import com.kh.finalProject.dto.PointListDto;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Point;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
    public final PointRepository pointRepository;
    public final MemberRepository memberRepository;

    // 포인트 적립
    public boolean addPoints(Long memberNum, int points, String pointType) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);

        if(memberOptional.isPresent()) {
            Member member = memberOptional.get();
            Point point = new Point();
            point.setPoint(points);
            point.setPointType(pointType);
            point.setMember(member);
            point.setPointDate(LocalDate.now());

            pointRepository.save(point);
            member.setTotalPoint(member.getTotalPoint() + points);
            memberRepository.save(member);
        }
        return true;
    }

    // 포인트 조회
    public List<PointDto> getPointList(Long memberNum) {
        Member member = new Member();
        member.setMemberNum(memberNum);
        List<Point> pointList = pointRepository.findByMember(member);

        List<PointDto> pointDtoList = new ArrayList<>();
        for (Point point : pointList) {
            PointDto pointDto = new PointDto();
            pointDto.setId(point.getId());
            pointDto.setPoint(point.getPoint());
            pointDto.setMemberNum(point.getMember().getMemberNum());
            pointDto.setPointType(point.getPointType());
            pointDtoList.add(pointDto);
        }
        return pointDtoList;
    }

    // 포인트 충전
    public Boolean chargePointByMemberNum(Long memberNum, Long chargingPoint) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);
        AtomicBoolean result = new AtomicBoolean(false);
        memberOptional.ifPresent(member -> {
            int newTotalPoint = member.getTotalPoint() + chargingPoint.intValue();
            member.setTotalPoint(newTotalPoint);
            memberRepository.save(member);
            result.set(true);
        });
        return result.get();
    }

    // 회원 번호를 가지고 시작 날짜와 종료 날짜 사이의 포인트 내역 조회
    public List<PointDto> getPointListByNumAndDate(Long memberNum, LocalDate startDate, LocalDate endDate) {
        Member member = new Member();
        member.setMemberNum(memberNum);
        List<Point> pointList = pointRepository.findByMemberAndPointDateBetween(member ,startDate, endDate);

        List<PointDto> pointDtoList = new ArrayList<>();
        for (Point point : pointList) {
            PointDto pointDto = new PointDto();
            pointDto.setId(point.getId());
            pointDto.setPoint(point.getPoint());
            pointDto.setMemberNum(point.getMember().getMemberNum());
            pointDto.setPointType(point.getPointType());
            pointDto.setPointDate(point.getPointDate());
            pointDtoList.add(pointDto);
        }
        return pointDtoList;
    }
}
