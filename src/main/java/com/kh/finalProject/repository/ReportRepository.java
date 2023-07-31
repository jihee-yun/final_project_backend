package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Report;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByReportNum(Long ReportNum);
    List<Report> findByUserIdAndReportDateBetween(String userId, LocalDate startDate, LocalDate endDate);
    // 회원 아이디로 문의, 신고 제복만 전체 조회
    @Query("SELECT report.title FROM Report report WHERE report.userId = :userId\n")
    List<String> findTitleByUserId(String userId);
}