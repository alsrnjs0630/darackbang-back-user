package com.lab.darackbang.service.event;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.event.EventResponseDTO;
import com.lab.darackbang.dto.event.EventSearchDTO;
import io.jsonwebtoken.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    PageDTO<EventResponseDTO, EventSearchDTO> getList(EventSearchDTO searchDTO, Pageable pageable) throws IOException;
}
