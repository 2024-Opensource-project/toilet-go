package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.dataAccess.entity.Toilet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomToiletRepository {
    Page<Toilet> findAllByFilter(ToiletDTO filter, Pageable pageable);
    List<Toilet> findNears(double[] lat, double[] lng);
}
