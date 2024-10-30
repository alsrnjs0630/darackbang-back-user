package com.lab.darackbang.service.member;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.MemberRole;
import com.lab.darackbang.entity.Role;
import com.lab.darackbang.exception.UserNotFoundException;
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
import java.util.function.Consumer;

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
        Member member = authenticationVerification();

        // 각 필드별로 업데이트 내용 반영
        updateField(member::setName, member.getName(), memberDTO.getName());
        updateField(member::setGender, member.getGender(), memberDTO.getGender());
        updateField(member::setAddress, member.getAddress(), memberDTO.getAddress());
        updateField(member::setPostNo, member.getPostNo(), memberDTO.getPostNo());
        updateField(member::setAddShippingAddr, member.getAddShippingAddr(), memberDTO.getAddShippingAddr());
        updateField(member::setAddPostNo, member.getAddPostNo(), memberDTO.getAddPostNo());
        updateField(member::setShippingAddr, member.getShippingAddr(), memberDTO.getShippingAddr());
        updateField(member::setShipPostNo, member.getShipPostNo(), memberDTO.getShipPostNo());
        updateField(member::setMobileNo, member.getMobileNo(), memberDTO.getMobileNo());
        updateField(member::setPhoneNo, member.getPhoneNo(), memberDTO.getPhoneNo());
        updateField(member::setMemberState, member.getMemberState(), memberDTO.getMemberState());

        memberRepository.save(member);
        return Map.of("RESULT", "SUCCESS");
    }

    // 이메일 중복 검사
    @Override
    public Map<String, String> eamilCheck(String userEmail){
        Optional<Member> member = memberRepository.findByUserEmail(userEmail);
        log.info("입력한 회원 이메일 : " + userEmail);
        if (member.isPresent() && member.get().getMemberState().equals("01")) {
            return Map.of("RESULT", "EXIST");
        } else {
            return Map.of("RESULT", "NON-EXIST");
        }
    }

    /*
    *  비밀번호 찾기 -> 이메일과 생년월일로 유저 본인인증 후 비밀번호 재설정
    */
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

    /*
    * 비밀번호 재설정
    * */
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
    * 회원정보 업데이트 메소드
    * */
    private <T> void updateField(Consumer<T> setter, T current, T updated) {
        if (!Objects.equals(current, updated)) {
            setter.accept(updated);
        }
    }

    /*
     * 현재 로그인한 (인증된)사용자 정보
     * */
    private Member authenticationVerification() {
        // 현재 로그인한 회원 정보 로딩
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        try{
            if (principal instanceof LoginDTO) {
                LoginDTO loginDTO = (LoginDTO) principal;

                Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail()).orElseThrow();
                return member;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("회원정보 불러오기 실패: ",e.getMessage());
            throw new UserNotFoundException(e.getMessage(), e.getCause());
        }

    }
}
