package com.lab.darackbang.service;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.MemberRole;
import com.lab.darackbang.entity.Role;
import com.lab.darackbang.mapper.MemberMapper;
import com.lab.darackbang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member join(MemberDTO memberDTO) {

        memberDTO.setMemberState("01");
        memberDTO.setIsBlacklist(Boolean.FALSE);
        memberDTO.setIsDeleted(Boolean.FALSE);
        memberDTO.setMileage(0);

        Member member = memberMapper.toEntity(memberDTO);

        // MemberRole 생성 및 Member 연결
        MemberRole memberRole = MemberRole.builder()
                .role(Role.USER)
                .member(member) // 반드시 member 객체를 설정
                .build();

        // Member에 MemberRole 추가
        member.setMemberRoles(List.of(memberRole));

        // Member 저장
        return memberRepository.save(member);
    }
}
