package com.myspring.springmaster.dataAccess.mapper;


import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HouseMapper {
    HouseMapper Instance = Mappers.getMapper(HouseMapper.class);

    HouseDTO toDTO(House house);
    House toEntity(HouseDTO houseDTO);
}
