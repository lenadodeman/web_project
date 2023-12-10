package com.example.web_project.api.mapper;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.dto.SerieAcessDTO;
import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.SerieAccess;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SerieAcessMapper {

    SerieAcessMapper INSTANCE = Mappers.getMapper(SerieAcessMapper.class);

    @Mapping(target = "serie", ignore = true)
    @Mapping(target = "user", ignore = true)
    SerieAcessDTO toDTO(SerieAccess serieAccess);

    SerieAccess toDomain(SerieAcessDTO serieAcessDTO);


}
