package com.lab.darackbang.controller;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/member/join")
    public String joinPage() {
        return "회원가입 페이지";
    }

    @PostMapping("/member/join")
    @Transactional
    public String join(MemberDTO memberDTO) {
        String inputPw = passwordEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(inputPw);
        memberService.join(memberDTO);
        return "회원가입 성공";
    }


}
