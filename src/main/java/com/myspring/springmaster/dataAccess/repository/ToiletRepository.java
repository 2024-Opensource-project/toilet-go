package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.entity.Toilet;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToiletRepository extends JpaRepository<Toilet, Long>, CustomToiletRepository{
    @NotNull
    Page<Toilet> findAll(@NotNull Pageable pageable);
    @NotNull List<Toilet> findAll();
}
