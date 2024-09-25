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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> join(MemberDTO memberDTO) {

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

    // 회원정보 수정
    @Override
    public Map<String, String> update(MemberDTO memberDTO, Long id) {
        Member member = memberRepository.findById(id).orElseThrow(); // 기존 회원 정보

        Member updateMember = memberMapper.toEntity(memberDTO);     // 수정된 회원 정보

        if (!Objects.equals(member.getName(), updateMember.getName())) { // 이름 수정
            member.setName(updateMember.getName());
        }
        if (!Objects.equals(member.getGender(), updateMember.getGender())) { // 성별 수정
            member.setGender(updateMember.getGender());
        }
        if (!Objects.equals(member.getPostNo(), updateMember.getPostNo())) { // 주소 수정
            member.setPostNo(updateMember.getPostNo());
            member.setAddress(updateMember.getAddress());
        }
        if (!Objects.equals(member.getAddPostNo(), updateMember.getAddPostNo())) { // 추가 배송지 수정
            member.setAddPostNo(updateMember.getAddPostNo());
            member.setAddShippingAddr(updateMember.getAddShippingAddr());
        }
        if (!Objects.equals(member.getShipPostNo(), updateMember.getShipPostNo())) { // 기본 배송지 수정
            member.setShipPostNo(updateMember.getShipPostNo());
            member.setShippingAddr(updateMember.getShippingAddr());
        }
        if (!Objects.equals(member.getMobileNo(), updateMember.getMobileNo())) { // 휴대폰번호 수정
            member.setMobileNo(updateMember.getMobileNo());
        }
        if (!Objects.equals(member.getPhoneNo(), updateMember.getPhoneNo())) { // 전화번호 수정
            member.setPhoneNo(updateMember.getPhoneNo());
        }

        /*
        Objects.equals() 사용: 이 메서드는 두 값이 동일한지 비교하며, 한쪽 또는 양쪽 모두 null인 경우에도 안전하게 처리합니다.
            즉, null.equals()로 인한 NullPointerException이 발생하지 않습니다.
        필드 비교: 각 필드를 비교할 때 Objects.equals()를 사용하여 null을 안전하게 처리한 후 변경된 값만 업데이트합니다.*/

        System.out.println("AddPostNo: " + memberDTO.getAddPostNo());
        System.out.println("AddShippingAddr: " + memberDTO.getAddShippingAddr());

        System.out.println("Mapped AddPostNo: " + updateMember.getAddPostNo());
        System.out.println("Mapped AddShippingAddr: " + updateMember.getAddShippingAddr());

        memberRepository.save(member);

        return Map.of("RESULT", "SUCCESS");
    }
}
