package com.kh.finalProject.controller;

import com.kh.finalProject.dto.MemberDto;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService=memberService;
    }

    // 로그인
    @PostMapping("/login")
    public String memberLogin(@RequestBody Map<String, String> loginData) {
        String userId = loginData.get("userId");
        String pw = loginData.get("pw");
        System.out.println("아이디 패스워드 확인 : "+userId + " " + pw);
        boolean result = memberService.loginCheck(userId, pw);
        return Boolean.toString(result);
    }

    // 회원가입
    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody Map<String, String> signData) {
        try {
            String userId = signData.get("userId");
            String name = signData.get("name");
            String pw = signData.get("pw");
            boolean result = memberService.regMember(userId, name, pw);
            log.warn(String.valueOf(result));
            if (result) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.warn("값이 false");
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.warn("Controller 오류");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    private MemberRepository memberRepository;

    // 아이디 찾기
    @GetMapping("/findId")
    public ResponseEntity<List<MemberDto>> findId(@RequestBody Map<String, String> find) {
        String email = find.get("email");
        List<MemberDto> foundMembers = new ArrayList<>();
        try {
            // 이메일을 기반으로 아이디 찾기
            Optional<Member> memberOptional = memberRepository.findByEmail(email);
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                MemberDto memberDto = new MemberDto();
                memberDto.setUserId(member.getUserId());
                foundMembers.add(memberDto);
            }

            if (!foundMembers.isEmpty()) {
                return ResponseEntity.ok(foundMembers);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 비밀번호 찾기
    @GetMapping("/findPw")
    public ResponseEntity<Boolean> findPw(@RequestBody Map<String, String> find) {
        String email = find.get("email");

        String resultString = memberService.findPw(email);
        boolean result =  Boolean.parseBoolean(resultString);

        if(result == true) return new ResponseEntity<>(true, HttpStatus.OK);
        else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    // 회원가입 중복
    @GetMapping("/check")
    public ResponseEntity<Boolean> doubleCheck(@RequestParam("userId") String userId) {
        try {
            boolean result = memberService.checkUserIdExist(userId);
            if(result) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}

