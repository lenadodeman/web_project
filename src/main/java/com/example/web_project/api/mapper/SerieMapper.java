package com.example.web_project.api.mapper;

import com.example.web_project.api.dto.SerieDTO;
import com.example.web_project.api.model.Serie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SerieMapper {

    SerieMapper INSTANCE = Mappers.getMapper(SerieMapper.class);

    @Mapping(target = "eventList", ignore = true)
    SerieDTO toDTO(Serie serie);

    Serie toDomain(SerieDTO serieDTO);

    List<SerieDTO> toDTOList(List<Serie> series);
}
