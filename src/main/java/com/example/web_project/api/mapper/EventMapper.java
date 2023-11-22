package com.example.web_project.api.mapper;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {TagMapper.class})
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDTO eventToEventDTO(Event event);
    Event eventDTOToEvent(EventDTO eventDTO);

    List<EventDTO> eventsToEventsDTO(List<Event> events);

}
