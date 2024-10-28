package com.lab.darackbang.service.event;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.event.EventDTO;
import com.lab.darackbang.dto.event.EventResponseDTO;
import com.lab.darackbang.dto.event.EventSearchDTO;
import io.jsonwebtoken.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    PageDTO<EventResponseDTO, EventSearchDTO> getList(EventSearchDTO searchDTO, Pageable pageable) throws IOException;
    EventDTO findOne(Long id) throws IOException;
    ResponseEntity<Resource> getEventFile(String fileName);
    List<String> fileNameList();
}
