package com.lab.darackbang.service;

import com.lab.darackbang.dto.member.MemberDTO;

import java.util.Map;

public interface MemberService {
    Map<String,String> join(MemberDTO memberDTO);   // 회원가입
    MemberDTO read();        // 회원 상세정보 (마이페이지 회원정보)
    Map<String,String> update(MemberDTO memberDTO); // 회원정보 수정
    Map<String,String> eamilCheck(String userEmail); // 아이디 중복 검사
    Map<String,String> searchPw(String userEamil, String birthday); // 비밀번호 찾기 1.아이디 검색
    Map<String,String> resetPw(String userEmail, String password, String passwordCheck); // 비밀번호 찾기 2.비밀번호 재설정
}
