package com.kh.finalProject.controller;


import com.kh.finalProject.dto.*;
import com.kh.finalProject.service.AdminService;
import com.kh.finalProject.service.ReportService;
import com.kh.finalProject.service.ReviewService;
import com.kh.finalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {
    private final ReportService reportService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final AdminService adminService;

    // 관리자 등록
    @PostMapping("/register")
    public ResponseEntity<AdminResponseDto> adminSignUp(@RequestBody AdminRequestDto adminRequestDto) {
        System.out.println("서버에서 받은 비밀번호: " + adminRequestDto.getPassword());
        return ResponseEntity.ok(adminService.adminSignUp(adminRequestDto));
    }

    // 관리자 로그인
    @PostMapping("/login")
    public ResponseEntity<AdminDto> adminLogin(@RequestBody Map<String, String> loginData) {
        String adminId = loginData.get("adminId");
        String password = loginData.get("password");

        AdminDto adminDto = adminService.findByAdminIdAndPassWord(adminId, password);

        if (adminDto != null) {
            // 로그인 성공한 경우
            return new ResponseEntity<>(adminDto, HttpStatus.OK);
        } else {
            // 로그인 실패한 경우
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 전체 회원 조회
    @GetMapping("/usermanage")
    public ResponseEntity<List<MemberDto>> userAll() {
        List<MemberDto> list = adminService.findAllUserList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 신고 전제 조회
    @GetMapping("/report/all")
    public ResponseEntity<List<ReportDto>> reportAll() {
        List<ReportDto> list = reportService.getReportList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 리뷰 전체 조회
    @GetMapping("/review/all")
    public ResponseEntity<List<ReviewDto>> reviewAll() {
        List<ReviewDto> list = reviewService.getAdminReview();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // reportNum 리뷰 조회
    @GetMapping("/review/getbynum")
    public ResponseEntity<List<ReviewDto>> reviewByNum(@RequestParam Long reviewNum) {
        List<ReviewDto> list = reviewService.getAdminReviewNum(reviewNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 번호로 신고 조회
    @GetMapping("/report/getbynum")
    public ResponseEntity<List<ReportDto>> reportByNum(@RequestParam Long reportNum) {
        List<ReportDto> list = reportService.getAllReportsByReportNum(reportNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 신고 내용 보기
    @GetMapping("/report/getContents")
    public ResponseEntity<List<ReportDto>> getReportContent(@RequestParam Long reportNum) {
        List<ReportDto> list = reportService.getReportContent(reportNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 사용자 관리 삭제
    @DeleteMapping("/usermanage/delete/{memberNum}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberNum) {
        boolean isDeleted = adminService.deleteMemberByMemberNum(memberNum);
        if (isDeleted) {
            return new ResponseEntity<>("사용자가 성공적으로 삭제되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("사용자를 찾을 수 없거나 삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 리뷰 관리 삭제
    @DeleteMapping("/review/delete/{reviewNum}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewNum) {
        boolean isDeleted = adminService.deleteReviewByReviewNum(reviewNum);
        if(isDeleted) {
            return new ResponseEntity<>("리뷰가 성공적으로 삭제되었습니다.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("리뷰 삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 신고 내역 삭제
    @DeleteMapping("/report/delete/{reportNum}")
    public ResponseEntity<String> deleteReport(@PathVariable Long reportNum) {
        boolean isDeleted = adminService.deleteReportByReportNum(reportNum);
        if(isDeleted) {
            return new ResponseEntity<>("신고정보가 성공적으로 삭제되었습니다.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("신고 삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

}
