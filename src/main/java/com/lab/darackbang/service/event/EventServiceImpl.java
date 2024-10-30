package com.lab.darackbang.service.event;

import com.lab.darackbang.common.utils.CustomFileUtil;
import com.lab.darackbang.criteria.EventCriteria;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.event.EventDTO;
import com.lab.darackbang.dto.event.EventResponseDTO;
import com.lab.darackbang.dto.event.EventSearchDTO;
import com.lab.darackbang.entity.Event;
import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.mapper.event.EventMapper;
import com.lab.darackbang.repository.EventRepository;
import com.lab.darackbang.service.product.ProductServiceImpl;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final PageMapper pageMapper;
    private final CustomFileUtil customFileUtil;

    // 이벤트 목록
    @Override
    @Transactional(readOnly = true)
    public PageDTO<EventResponseDTO, EventSearchDTO> getList(EventSearchDTO searchDTO, Pageable pageable) {
        log.info("요청 데이터: {}", searchDTO);

        // EventSearchDTO에 기반하여 Specification<Event> 객체 생성
        Specification<Event> spec = EventCriteria.byCriteria(searchDTO);

        //페이지 번호(프론트에서는 1부터 시작하지만 실제로는 현재 페이지번호 -1)
        int pageNumber = (pageable.getPageNumber() < 1) ? 0 : pageable.getPageNumber() - 1;
        Pageable correctedPageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        // JPA 리포지토리를 사용하여 페이징을 적용한 상품 목록 조회 후, EventMapper를 통해 EventDTO로 변환 후 Page객체 생성
        return pageMapper.toDTO(eventRepository.findAll(spec, correctedPageable).map(eventMapper::toResponseDTO),searchDTO);
    }

    @Override
    public EventDTO findOne(Long id) throws IOException {
        return eventMapper.toDTO(eventRepository.findById(id).orElse(null));
    }

    @Override
    public ResponseEntity<Resource> getEventFile(String fileName) {
        return customFileUtil.getEventFile(fileName);
    }

    @Override
    public List<String> fileNameList() {
        return eventRepository.findAllFileNames();
    }
}
