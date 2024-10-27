package com.lab.darackbang.mapper.event;

import com.lab.darackbang.dto.event.EventDTO;
import com.lab.darackbang.dto.event.EventResponseDTO;
import com.lab.darackbang.entity.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventDTO eventDTO);
    EventDTO toDTO(Event event);
    EventResponseDTO toResponseDTO(Event event);
    List<EventResponseDTO> eventToResponseDTO(List<Event> event);
}
