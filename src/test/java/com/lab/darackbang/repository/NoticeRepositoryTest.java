package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Notice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class NoticeRepositoryTest {
    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void readNotice() {
        Notice notice = noticeRepository.findById(11L).orElseThrow();

        log.info("공지사항 상세정보: " + notice.toString());
    }
}
