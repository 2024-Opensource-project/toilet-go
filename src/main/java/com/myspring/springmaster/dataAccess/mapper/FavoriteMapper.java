package com.myspring.springmaster.dataAccess.mapper;

import com.myspring.springmaster.dataAccess.DTO.FavoriteDTO;
import com.myspring.springmaster.dataAccess.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    FavoriteMapper Instance = Mappers.getMapper(FavoriteMapper.class);
    FavoriteDTO toDTO(Favorite favorite); //entity->DTO
    Favorite toEntity(FavoriteDTO favoriteDTO); //DTO->entity
    List<FavoriteDTO> toDTO(List<Favorite> favoriteList);
    List<Favorite> toEntity(List<FavoriteDTO> favoriteDTOList);
}
