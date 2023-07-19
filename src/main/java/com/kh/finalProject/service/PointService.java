package com.kh.finalProject.service;

import com.kh.finalProject.dto.PointDto;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Point;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public boolean addPoint(int totalPoint) {
        Point point = new Point();
        point.setTotalPoint(totalPoint);
        Point savePoint = pointRepository.save(point);
        return true;
    }

    // 포인트 조회
    public List<PointDto> getPointList(Long memberNum, Long pointId) {
        Optional<Member> member = memberRepository.findByMemberNum(memberNum);
        List<PointDto> pointDtos = new ArrayList<>();
        if(member.isPresent()) {
            List<Point> points = pointRepository.findByMember(member.get());
            for (Point point : points) {
                PointDto pointDto = new PointDto();
                pointDto.setId(point.getId());
                pointDto.setTotalPoint(point.getTotalPoint());
                pointDto.setMemberName(member.get().getName());

                pointDtos.add(pointDto);
            }
        }
        return pointDtos;
    }

    // 포인트 충전
    public Boolean chargePointByMemberNum(Long memberNum, Long chargingPoint) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);
        AtomicBoolean result = new AtomicBoolean(false);
        memberOptional.ifPresent(member -> {
            Point point = pointRepository.findByMember(member).get(0);
            int newTotalPoint = point.getTotalPoint() + chargingPoint.intValue();  // 기존 포인트에 충전 포인트를 더합니다.
            point.setTotalPoint(newTotalPoint);
            pointRepository.save(point);
            result.set(true);
        });
        return result.get();
    }
}
