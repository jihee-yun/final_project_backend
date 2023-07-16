package com.kh.finalProject.service;

import com.kh.finalProject.dto.ReportDto;
import com.kh.finalProject.entity.Report;
import com.kh.finalProject.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

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

    public List<ReportDto> getReportByNumAndDate(String userId, LocalDate startDate, LocalDate endDate) {
        List<Report> reportList = reportRepository.findByUserIdAndReportDateBetween(userId, startDate, endDate);
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

}
