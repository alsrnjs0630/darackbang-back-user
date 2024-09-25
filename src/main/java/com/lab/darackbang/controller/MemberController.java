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

    // 회원 상세정보
    @GetMapping("/member/{id}")
    public MemberDTO getMember(@PathVariable Long id) {
        return memberService.read(id);
    }





}
