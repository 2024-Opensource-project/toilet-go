package com.myspring.springmaster.dataAccess.mapper;


import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.dataAccess.entity.HouseDetail;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseMapper {
    HouseMapper Instance = Mappers.getMapper(HouseMapper.class);

    HouseDTO toDTO(House house);
    House toEntity(HouseDTO houseDTO);
}
