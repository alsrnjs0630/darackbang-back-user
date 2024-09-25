package com.lab.darackbang.service;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.MemberRole;
import com.lab.darackbang.entity.Role;
import com.lab.darackbang.mapper.MemberMapper;
import com.lab.darackbang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Map<String,String> join(MemberDTO memberDTO) {

        memberDTO.setMemberState("01");
        memberDTO.setIsBlacklist(Boolean.FALSE);
        memberDTO.setIsDeleted(Boolean.FALSE);
        memberDTO.setMileage(0);
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

        Member member = memberMapper.toEntity(memberDTO);

        // MemberRole 생성 및 Member 연결
        MemberRole memberRole = MemberRole.builder()
                .role(Role.USER)
                .member(member) // 반드시 member 객체를 설정
                .build();

        // Member에 MemberRole 추가
        member.setMemberRoles(List.of(memberRole));

        memberRepository.save(member);

        // MembMer 저장
        return Map.of("RESULT", "SUCCESS");
    }

    // 마이페이지 회원정보
    @Override
    public MemberDTO read(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return memberMapper.toDTO(member);
    }

}
