package com.lab.darackbang.service;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.entity.Member;

public interface MemberService {
    // 회원가입
    Member join(MemberDTO memberDTO);
}
