package com.example.web_project.api.mapper;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "tags", ignore = true)
    EventDTO toDTO(Event event);
    Event toDomain(EventDTO eventDTO);

    List<EventDTO> toDTOList(List<Event> events);

}
