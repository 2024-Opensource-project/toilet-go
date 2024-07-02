package com.myspring.springmaster.dataAccess.mapper;


import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.entity.House;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HouseMapper {
    HouseMapper Instance = Mappers.getMapper(HouseMapper.class);

    HouseDTO toHouseDTO(House house);
}
