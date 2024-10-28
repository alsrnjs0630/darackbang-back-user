package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>,
        JpaSpecificationExecutor<Event> {
    // 진행중인 Event의 fileName만 추출하여 리스트로 반환하는 메서드
    @Query("SELECT fileName FROM Event WHERE eventState = '02'")
    List<String> findAllFileNames();
}
