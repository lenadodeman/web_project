package com.example.web_project.api.mapper;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.dto.TagDTO;
import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper()
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Mapping(target = "events", ignore = true)
    TagDTO toDTO(Tag tag);
    Tag toDomain(TagDTO tagDTO);

    List<TagDTO> toDTOList(List<Tag> tagList);
}
