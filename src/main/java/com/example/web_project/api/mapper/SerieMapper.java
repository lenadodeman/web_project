package com.example.web_project.api.mapper;

import com.example.web_project.api.dto.SerieDTO;
import com.example.web_project.api.model.Serie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SerieMapper {

    SerieMapper INSTANCE = Mappers.getMapper(SerieMapper.class);

    Serie toDomain(SerieDTO serieDTO);
    SerieDTO toDTO(Serie serie);

    List<SerieDTO> toDTOList(List<Serie> serieList);
}
