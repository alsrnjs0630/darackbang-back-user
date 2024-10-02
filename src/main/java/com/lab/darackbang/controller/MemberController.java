package com.lab.darackbang.controller;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/member/join")
    public String joinPage() {
        return "회원가입 페이지";
    }

    // 회원가입
    @PostMapping("/member/join")
    public Map<String, String> join(MemberDTO memberDTO) {
        return memberService.join(memberDTO);
    }

    // 마이페이지 회원정보
    @GetMapping("/member/info")
    public MemberDTO getMember() {
        return memberService.read();
    }

    // 마이페이지 회원정보 수정
    @PutMapping("/member/info")
    public Map<String,String> updateMember(MemberDTO memberDTO) {
        return memberService.update(memberDTO);
    }

    // 아이디 중복 검사
    @PostMapping("/member/emailcheck")
    public Map<String,String> emailCheck(String userEmail) {
        log.info("입력한 회원 이메일 컨트롤러 : " + userEmail);
        return memberService.eamilCheck(userEmail);
    }

    // 비밀번호 찾기
    @PostMapping("/member/searchpw")
    public Map<String,String> searchpw(String userEmail, String birthday) {
        return memberService.searchPw(userEmail,birthday);
    }

    // 비밀번호 재설정
    @PostMapping("/member/resetpw")
    public Map<String,String> resetpw(String userEmail, String password, String passwordCheck) {
        return memberService.resetPw(userEmail,password,passwordCheck);
    }
}
