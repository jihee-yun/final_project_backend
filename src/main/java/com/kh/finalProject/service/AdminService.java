package com.kh.finalProject.service;

import com.kh.finalProject.dto.ReportDto;
import com.kh.finalProject.dto.ReviewDto;
import com.kh.finalProject.dto.UserDto;
import com.kh.finalProject.entity.Report;
import com.kh.finalProject.entity.Review;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.repository.ReportRepository;
import com.kh.finalProject.repository.ReviewRepository;
import com.kh.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReportRepository reportRepository;


    // 전체 회원 조회
    public List<UserDto> findAllUserList(UserDetails userDetails, HttpServletRequest request) {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users) {
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setEmail(user.getEmail());
            userDto.setPhone(user.getPhone());
            userDto.setGender(user.getGender());
            userDto.setBirthday(user.getBirthday());
            userDto.setAuthority(user.getAuthority());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    // 전체 리뷰 조회
    public List<ReviewDto> findAllReviewList(UserDetails userDetails, HttpServletRequest request) {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for(Review review : reviews) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewNum(review.getReviewNum());
            reviewDto.setUserNum(review.getUserNum());
            reviewDto.setCafeNum(review.getCafeNum());
            reviewDto.setReviewContent(review.getReviewContent());
            reviewDto.setReviewImgUrl1(review.getReviewImgUrl1());
            reviewDto.setReviewImgUrl2(review.getReviewImgUrl2());
            reviewDto.setWrittenTime(review.getWrittenTime());
            reviewDto.setLikeCount(review.getLikeCount());
            reviewDto.setScore(review.getScore());
            reviewDtos.add(reviewDto);
        }
        return reviewDtos;
    }

    // 전체 신고 조회
    public List<ReportDto> findAllReportList(UserDetails userDetails, HttpServletRequest request) {
        List<Report> reports = reportRepository.findAll();
        List<ReportDto> reportDtos = new ArrayList<>();
        for(Report report : reports) {
            ReportDto reportDto = new ReportDto();
            reportDto.setReportNum(report.getReportNum());
            reportDto.setUserId(report.getUserId());
            reportDto.setContent(report.getReportContent());
            reportDto.setTitle(report.getTitle());
            reportDtos.add(reportDto);
        }
        return reportDtos;
    }
}
