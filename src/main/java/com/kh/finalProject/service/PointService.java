package com.kh.finalProject.service;

import com.kh.finalProject.dto.PointDto;
import com.kh.finalProject.entity.Point;
import com.kh.finalProject.repository.PointRepository;
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
public class PointService {
    public final PointRepository pointRepository;

    // 포인트 적립
    public boolean addPoint(int totalPoint) {
        Point point = new Point();
        point.setTotalPoint(totalPoint);
        Point savePoint = pointRepository.save(point);
        return true;
    }

    // 포인트 조회
    public List<PointDto> getPointList() {
        List<Point> points = pointRepository.findAll();
        List<PointDto> pointDtoList = new ArrayList<>();
        for (Point point : points) {
            PointDto pointDto = new PointDto();
            pointDto.setTotalPoint(point.getTotalPoint());
            pointDto.setName(point.getUser().getName());
        }
        return pointDtoList;
    }
}
