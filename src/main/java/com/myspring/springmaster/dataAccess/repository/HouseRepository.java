package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {

}
