package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.dataAccess.entity.Toilet;

import java.util.List;

public interface CustomToiletRepository {
    List<Toilet> findAllByFilter(ToiletDTO filter);
}
