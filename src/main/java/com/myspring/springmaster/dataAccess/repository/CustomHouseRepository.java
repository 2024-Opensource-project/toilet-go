package com.myspring.springmaster.dataAccess.repository;

import java.util.List;

public interface CustomHouseRepository {
    List<House> findAllByFilter(HouseDTO filter);
}
