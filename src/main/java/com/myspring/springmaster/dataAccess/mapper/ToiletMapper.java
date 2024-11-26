package com.myspring.springmaster.dataAccess.mapper;

import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.dataAccess.entity.Toilet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ToiletMapper {
    ToiletMapper Instance = Mappers.getMapper(ToiletMapper.class);
    ToiletDTO toDTO(Toilet toilet); //entity->DTO
    Toilet toEntity(ToiletDTO toiletDTO); //DTO->entity
}
