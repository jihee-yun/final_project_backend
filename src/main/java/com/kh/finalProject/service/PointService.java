package com.kh.finalProject.service;

import com.kh.finalProject.entity.Point;
import com.kh.finalProject.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
    public final PointRepository pointRepository;

    public boolean addPoint(int totalPoint) {
        Point point = new Point();
        point.setTotalPoint(totalPoint);
        Point savePoint = pointRepository.save(point);
        return true;
    }
}
