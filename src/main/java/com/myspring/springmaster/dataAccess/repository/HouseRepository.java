package com.myspring.springmaster.dataAccess.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>, CustomHouseRepository {
    @NotNull
    Page<House> findAll(@NotNull Pageable pageable);
    List<House> findAllByStatusOrderByIdDesc(@NotNull String status);
}
