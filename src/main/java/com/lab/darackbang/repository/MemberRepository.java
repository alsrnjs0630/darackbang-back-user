package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> ,
        JpaSpecificationExecutor<Member> {

    @EntityGraph(attributePaths = "memberRoles")
    Optional<Member> findByUserEmail(String userEmail);
}
