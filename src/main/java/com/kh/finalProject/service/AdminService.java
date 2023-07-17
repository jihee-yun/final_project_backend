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
}
