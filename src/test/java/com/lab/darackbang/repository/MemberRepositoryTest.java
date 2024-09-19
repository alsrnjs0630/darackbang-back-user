package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.MemberRole;
import com.lab.darackbang.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Test
    public void insertMember() {
        log.info("회원등록 테스트-----------------------------");

        for (int i = 0; i < 5; i++) {
            Member member = Member.builder()
                    .userEmail("userJoin" + String.valueOf(i) + "@test.com")
                    .password("1234")
                    .name("user" + String.valueOf(i))
                    .birthday("20240302")
                    .ageGroup("20")
                    .gender("F")
                    .mobileNo("01028810137")
                    .mileage(0)
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .build();
            List<MemberRole> memberRoles = new ArrayList<>();

            MemberRole userRole = new MemberRole();
            userRole.setRole(Role.USER);
            userRole.setMember(member);
            memberRoles.add(userRole);

            member.setMemberRoles(memberRoles);

            memberRepository.save(member);
        }
    }

    @Test
    public void updateMember() { // 회원정보 수정 테스트
        Member member = memberRepository.findById(5L).orElseThrow();

        member.setPassword("updateTest");
        member.setMobileNo("01099049212");
        member.setPhoneNo("01099998888");
        member.setGender("M");
        member.setAddPostNo("15010");
        member.setAddress("가산디지털단지");
        member.setMemberState("03");

        memberRepository.save(member);
    }
}
