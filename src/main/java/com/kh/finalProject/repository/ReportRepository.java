package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Report;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByUserId(String userId);
    List<Report> findByUserIdAndReportDateBetween(String userId, LocalDate startDate, LocalDate endDate);
}
