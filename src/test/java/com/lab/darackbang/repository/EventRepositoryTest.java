package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    public void readEvents() { // 이벤트 상세정보 테스트
        Event event = eventRepository.findById(4L).orElseThrow();

        log.info("이벤트 정보: " + event.toString());

    }
}
