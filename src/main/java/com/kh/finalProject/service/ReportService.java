package com.kh.finalProject.service;

import com.kh.finalProject.constant.QnaCategory;
import com.kh.finalProject.constant.RequestCategory;
import com.kh.finalProject.dto.ReportDto;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Report;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    public List<ReportDto> getReportList() {
        List<Report> reportList = reportRepository.findAll();
        List<ReportDto> reportDtoList = new ArrayList<>();
        for(Report report : reportList) {
            ReportDto reportDto = new ReportDto();
            reportDto.setReportNum(report.getReportNum());
            reportDto.setUserId(report.getUserId());
            reportDto.setContent(report.getReportContent());
            reportDto.setTitle(report.getTitle());
            reportDto.setReportDate(report.getReportDate());
            reportDtoList.add(reportDto);
        }
        return reportDtoList;
    }

    public List<ReportDto> getAllReportsByReportNum(Long reportNum) {
        List<Report> reportList = reportRepository.findAllByReportNum(reportNum);
        List<ReportDto> reportDtoList = new ArrayList<>();
        for(Report report : reportList) {
            ReportDto reportDto = new ReportDto();
            reportDto.setReportNum(report.getReportNum());
            reportDto.setUserId(report.getUserId());
            reportDto.setContent(report.getReportContent());
            reportDto.setTitle(report.getTitle());
            reportDto.setReportDate(report.getReportDate());
            reportDtoList.add(reportDto);
        }
        return reportDtoList;
    }

    public List<ReportDto> getReportContent(Long reportNum) {
        List<Report> reportList = reportRepository.findAllByReportNum(reportNum);
        List<ReportDto> reportDtoList = new ArrayList<>();
        for(Report report : reportList) {
            ReportDto reportDto = new ReportDto();
            reportDto.setReportNum(report.getReportNum());
            reportDto.setUserId(report.getUserId());
            reportDto.setContent(report.getReportContent());
            reportDto.setTitle(report.getTitle());
            reportDto.setReportDate(report.getReportDate());
            reportDtoList.add(reportDto);
        }
        return reportDtoList;
    }
    public ReportDto saveReport(ReportDto reportDto) {
        // ReportDto를 Report Entity로 변환
        Report report = new Report();
        report.setUserId(reportDto.getUserId());
        report.setReportContent(reportDto.getContent());
        report.setTitle(reportDto.getTitle());
        report.setReportDate(reportDto.getReportDate());

        // 데이터베이스에 저장
        Report savedReport = reportRepository.save(report);

        // 저장된 Report Entity를 ReportDto로 변환하여 반환
        ReportDto savedReportDto = new ReportDto();
        savedReportDto.setReportNum(savedReport.getReportNum());
        savedReportDto.setUserId(savedReport.getUserId());
        savedReportDto.setContent(savedReport.getReportContent());
        savedReportDto.setTitle(savedReport.getTitle());
        savedReportDto.setReportDate(savedReport.getReportDate());

        return savedReportDto;
    }

    // 고객센터 문의사항 등록
    public boolean regQuestion(Long userNum, String title, String content, QnaCategory qnaCategory, RequestCategory requestCategory) {
        Optional<Member> member = memberRepository.findByMemberNum(userNum);
        if(member.isPresent()) {
            Report report = new Report();
            report.setUserId(member.get().getMemberId());
            report.setTitle(title);
            report.setReportContent(content);
            report.setReportDate(LocalDate.now());
            report.setMemberType(qnaCategory);
            report.setCategory(requestCategory);
            Report newReport = reportRepository.save(report);
            return true;
        } else {
            throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다.");
        }
    }
}
