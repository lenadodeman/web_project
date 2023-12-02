package com.example.web_project.api.mapper;

import com.example.web_project.api.dto.UserDTO;

import com.example.web_project.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toDomain(UserDTO userDTO);
    UserDTO toDTO(User user);

    List<UserDTO> toDTOList(List<User> userList);
}
