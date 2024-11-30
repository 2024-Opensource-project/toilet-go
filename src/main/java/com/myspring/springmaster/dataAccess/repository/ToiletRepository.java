package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.entity.Toilet;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToiletRepository extends JpaRepository<Toilet, Long>, CustomToiletRepository{
    @NotNull
    Page<Toilet> findAll(@NotNull Pageable pageable);
    @NotNull List<Toilet> findAll();
}
