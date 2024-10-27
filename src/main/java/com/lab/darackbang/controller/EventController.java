package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.event.EventResponseDTO;
import com.lab.darackbang.dto.event.EventSearchDTO;
import com.lab.darackbang.mapper.event.EventMapper;
import com.lab.darackbang.repository.EventRepository;
import com.lab.darackbang.service.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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

}
