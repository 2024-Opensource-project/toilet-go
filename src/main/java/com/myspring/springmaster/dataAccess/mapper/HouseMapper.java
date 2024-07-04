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

    @AfterMapping
    default House afterToEntity(@MappingTarget House.HouseBuilder houseBuilder) {
        House house = houseBuilder.build();
        for(HouseDetail houseDetail : house.getHouseDetails()) {
            houseDetail.setHouse(house);
        }
        return house;
    }
    /*
    default HouseDetail houseDetailDTOToHouseDetail(HouseDetailDTO houseDetailDTO, House house) {
        if ( houseDetailDTO == null ) {
            return null;
        }

        HouseDetail.HouseDetailBuilder<?, ?> houseDetail = HouseDetail.builder();

        houseDetail.id( houseDetailDTO.getId() );
        houseDetail.house(house);
        houseDetail.type( houseDetailDTO.getType() );
        houseDetail.size( houseDetailDTO.getSize() );
        houseDetail.supplyCount( houseDetailDTO.getSupplyCount() );
        houseDetail.deposit( houseDetailDTO.getDeposit() );
        houseDetail.monthlyRent( houseDetailDTO.getMonthlyRent() );
        houseDetail.imageUrl( houseDetailDTO.getImageUrl() );

        return houseDetail.build();
    }*/
}
