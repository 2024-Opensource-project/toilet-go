package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.entity.House;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    @NotNull
    Page<House> findAll(@NotNull Pageable pageable);
}
