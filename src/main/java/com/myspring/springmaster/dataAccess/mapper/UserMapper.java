package com.myspring.springmaster.dataAccess.mapper;

import com.myspring.springmaster.dataAccess.DTO.UserDTO;
import com.myspring.springmaster.dataAccess.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);

}
