package com.lab.darackbang.service;

import com.lab.darackbang.dto.member.MemberDTO;

import java.util.Map;

public interface MemberService {
    // 회원가입
    Map<String,String> join(MemberDTO memberDTO);
    // 회원 상세정보 (마이페이지 회원정보)
    MemberDTO read(Long id);
}
