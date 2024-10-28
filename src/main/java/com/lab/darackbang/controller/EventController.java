package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.event.EventDTO;
import com.lab.darackbang.dto.event.EventResponseDTO;
import com.lab.darackbang.dto.event.EventSearchDTO;
import com.lab.darackbang.mapper.event.EventMapper;
import com.lab.darackbang.repository.EventRepository;
import com.lab.darackbang.service.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventService eventService;

    // 이벤트 목록
    @GetMapping("/list")
    public PageDTO<EventResponseDTO, EventSearchDTO> getList(@ModelAttribute EventSearchDTO eventSearchDTO,
                                                             @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("서치 디티오: {}", eventSearchDTO);
        log.info("페이지 정보: {}", pageable);
        return eventService.getList(eventSearchDTO, pageable);
    }

    // 이벤트 상세 정보
    @GetMapping("/read/{id}")
    public EventDTO getOne(@PathVariable long id) {
        log.info("이벤트 상세정보 출력------------------");
        return eventService.findOne(id);
    }

    // 이벤트 이미지 가져오기
    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewFile(@PathVariable String filename) {
        return eventService.getEventFile(filename);
    }

    // 이벤트 파일명 리스트 가져오기
    @GetMapping("/filelist")
    public List<String> fileList() {
        log.info("파일 리스트 목록 출력 진행");
        return eventService.fileNameList();
    }

}
