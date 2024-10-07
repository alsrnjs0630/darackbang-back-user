package com.lab.darackbang.service;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.MemberRole;
import com.lab.darackbang.entity.Role;
import com.lab.darackbang.mapper.member.MemberMapper;
import com.lab.darackbang.repository.MemberRepository;
import com.lab.darackbang.security.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    /*
     * 회원가입
     * */
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

    /*
     * 마이페이지 회원정보 조회
     * */
    @Override
    public MemberDTO read() {
        Member member = authenticationVerification();
        if (member == null) {
            return null;
        } else {
            return memberMapper.toDTO(member);
        }
    }

    /*
     * 마이페이지 회원정보 수정
     * */
    @Override
    public Map<String, String> update(MemberDTO memberDTO) {

        Member member = authenticationVerification(); // 기존 회원 정보

        if (!Objects.equals(member.getName(), memberDTO.getName())) { // 이름 수정
            member.setName(memberDTO.getName());
        }

        if (!Objects.equals(member.getGender(), memberDTO.getGender())) { // 성별 수정
            member.setGender(memberDTO.getGender());
        }

        if (!Objects.equals(member.getAddress(), memberDTO.getAddress())) { // 주소 수정
            member.setPostNo(memberDTO.getPostNo());
            member.setAddress(memberDTO.getAddress());
        }

        if (!Objects.equals(member.getAddShippingAddr(), memberDTO.getAddShippingAddr())) { // 추가 배송지 수정
            member.setAddPostNo(memberDTO.getAddPostNo());
            member.setAddShippingAddr(memberDTO.getAddShippingAddr());
        }

        if (!Objects.equals(member.getShippingAddr(), memberDTO.getShippingAddr())) { // 기본 배송지 수정
            member.setShipPostNo(memberDTO.getShipPostNo());
            member.setShippingAddr(memberDTO.getShippingAddr());
        }

        if (!Objects.equals(member.getMobileNo(), memberDTO.getMobileNo())) { // 휴대폰번호 수정
            member.setMobileNo(memberDTO.getMobileNo());
        }

        if (!Objects.equals(member.getPhoneNo(), memberDTO.getPhoneNo())) { // 전화번호 수정
            member.setPhoneNo(memberDTO.getPhoneNo());
        }

        /*
        Objects.equals() 사용: 이 메서드는 두 값이 동일한지 비교하며, 한쪽 또는 양쪽 모두 null인 경우에도 안전하게 처리합니다.
            즉, null.equals()로 인한 NullPointerException이 발생하지 않습니다.
        필드 비교: 각 필드를 비교할 때 Objects.equals()를 사용하여 null을 안전하게 처리한 후 변경된 값만 업데이트합니다.*/

        log.debug("DTO : {}", memberDTO);

        memberRepository.save(member);

        return Map.of("RESULT", "SUCCESS");
    }

    // 아이디 중복 검사
    @Override
    public Map<String, String> eamilCheck(String userEmail) {
        Optional<Member> member = memberRepository.findByUserEmail(userEmail);
        log.info("입력한 회원 이메일 : " + userEmail);
        if (member.isPresent()) {
            return Map.of("RESULT", "EXIST");
        } else {
            return Map.of("RESULT", "NON-EXIST");
        }
    }

    // 비밀번호 찾기
    @Override
    public Map<String, String> searchPw(String userEmail, String birthday) {
        Optional<Member> member = memberRepository.findByUserEmail(userEmail);

        if (member.isPresent()) {
            if(member.get().getBirthday().equals(birthday)) {
                return Map.of("RESULT", "SUCCESS");
            } else {
                return Map.of("RESULT", "FAIL-BIRTHDAY-UNCORRECTED");
            }
        } else {
            return Map.of("RESULT", "FAIL");
        }
    }

    // 비밀번호 재설정
    @Override
    public Map<String, String> resetPw(String userEmail, String password, String passwordCheck) {
        Optional<Member> member = memberRepository.findByUserEmail(userEmail);

        if (member.isPresent()) {
            if(password.equals(passwordCheck)) {
                member.get().setPassword(passwordEncoder.encode(password));
                memberRepository.save(member.get());
                return Map.of("RESULT", "SUCCESS");
            }else {
                return Map.of("RESULT", "FAIL");
            }
        }else {
            return Map.of("RESULT", "FAIL-SERVER");
        }
    }

    /*
     * 현재 로그인한 (인증된)사용자 정보
     * */
    private Member authenticationVerification() {
        // 현재 로그인한 회원 정보 로딩
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof LoginDTO) {
            LoginDTO loginDTO = (LoginDTO) principal;

            Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail()).orElseThrow();
            return member;
        } else {
            return null;
        }
    }


}
