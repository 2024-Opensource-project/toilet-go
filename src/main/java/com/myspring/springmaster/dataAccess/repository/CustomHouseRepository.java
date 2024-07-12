package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.entity.House;

import java.util.List;

public interface CustomHouseRepository {
    List<House> findAllByFilter(HouseDTO filter);
}
