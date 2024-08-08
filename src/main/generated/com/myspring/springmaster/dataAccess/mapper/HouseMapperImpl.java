package com.myspring.springmaster.dataAccess.mapper;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.DTO.HouseDetailDTO;
import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.dataAccess.entity.HouseDetail;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-08T23:06:36+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class HouseMapperImpl implements HouseMapper {

    @Override
    public HouseDTO toDTO(House house) {
        if ( house == null ) {
            return null;
        }

        HouseDTO houseDTO = new HouseDTO();

        if ( house.getId() != null ) {
            houseDTO.setId( house.getId().intValue() );
        }
        houseDTO.setName( house.getName() );
        houseDTO.setAddress( house.getAddress() );
        houseDTO.setLatitude( house.getLatitude() );
        houseDTO.setLongitude( house.getLongitude() );
        houseDTO.setStatus( house.getStatus() );
        houseDTO.setMoveInDate( house.getMoveInDate() );
        houseDTO.setApplyStartDate( house.getApplyStartDate() );
        houseDTO.setApplyEndDate( house.getApplyEndDate() );
        houseDTO.setHouseDetails( houseDetailListToHouseDetailDTOList( house.getHouseDetails() ) );

        return houseDTO;
    }

    @Override
    public House toEntity(HouseDTO houseDTO) {
        if ( houseDTO == null ) {
            return null;
        }

        House.HouseBuilder house = House.builder();

        if ( houseDTO.getId() != null ) {
            house.id( houseDTO.getId().longValue() );
        }
        house.name( houseDTO.getName() );
        house.address( houseDTO.getAddress() );
        house.latitude( houseDTO.getLatitude() );
        house.longitude( houseDTO.getLongitude() );
        house.status( houseDTO.getStatus() );
        house.moveInDate( houseDTO.getMoveInDate() );
        house.applyStartDate( houseDTO.getApplyStartDate() );
        house.applyEndDate( houseDTO.getApplyEndDate() );
        house.houseDetails( houseDetailDTOListToHouseDetailList( houseDTO.getHouseDetails() ) );

        return house.build();
    }

    protected HouseDetailDTO houseDetailToHouseDetailDTO(HouseDetail houseDetail) {
        if ( houseDetail == null ) {
            return null;
        }

        HouseDetailDTO houseDetailDTO = new HouseDetailDTO();

        if ( houseDetail.getId() != null ) {
            houseDetailDTO.setId( houseDetail.getId().intValue() );
        }
        houseDetailDTO.setType( houseDetail.getType() );
        houseDetailDTO.setSize( houseDetail.getSize() );
        houseDetailDTO.setSupplyCount( houseDetail.getSupplyCount() );
        houseDetailDTO.setDeposit( houseDetail.getDeposit() );
        houseDetailDTO.setMonthlyRent( houseDetail.getMonthlyRent() );
        houseDetailDTO.setImageUrl( houseDetail.getImageUrl() );

        return houseDetailDTO;
    }

    protected List<HouseDetailDTO> houseDetailListToHouseDetailDTOList(List<HouseDetail> list) {
        if ( list == null ) {
            return null;
        }

        List<HouseDetailDTO> list1 = new ArrayList<HouseDetailDTO>( list.size() );
        for ( HouseDetail houseDetail : list ) {
            list1.add( houseDetailToHouseDetailDTO( houseDetail ) );
        }

        return list1;
    }

    protected HouseDetail houseDetailDTOToHouseDetail(HouseDetailDTO houseDetailDTO) {
        if ( houseDetailDTO == null ) {
            return null;
        }

        HouseDetail.HouseDetailBuilder<?, ?> houseDetail = HouseDetail.builder();

        if ( houseDetailDTO.getId() != null ) {
            houseDetail.id( houseDetailDTO.getId().longValue() );
        }
        houseDetail.type( houseDetailDTO.getType() );
        houseDetail.size( houseDetailDTO.getSize() );
        houseDetail.supplyCount( houseDetailDTO.getSupplyCount() );
        houseDetail.deposit( houseDetailDTO.getDeposit() );
        houseDetail.monthlyRent( houseDetailDTO.getMonthlyRent() );
        houseDetail.imageUrl( houseDetailDTO.getImageUrl() );

        return houseDetail.build();
    }

    protected List<HouseDetail> houseDetailDTOListToHouseDetailList(List<HouseDetailDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<HouseDetail> list1 = new ArrayList<HouseDetail>( list.size() );
        for ( HouseDetailDTO houseDetailDTO : list ) {
            list1.add( houseDetailDTOToHouseDetail( houseDetailDTO ) );
        }

        return list1;
    }
}
