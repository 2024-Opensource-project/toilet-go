package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.dataAccess.entity.QHouse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.myspring.springmaster.dataAccess.entity.QHouse.house;

@Repository
public class CustomHouseRepositoryImpl implements CustomHouseRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public CustomHouseRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<House> findAllByFilter(HouseDTO filter) {
        return jpaQueryFactory
                .selectFrom(house)
                .where(
                        containsAddress(filter.getAddress()),
                        containsName(filter.getName()),
                        equalStatus(filter.getStatus()),
                        betweenApplyDate(filter.getApplyStartDate(), filter.getApplyEndDate()))
                .fetch();
    }

    private BooleanExpression containsName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return house.name.contains(name);
    }

    private BooleanExpression containsAddress(String address) {
        if (address == null || address.isEmpty()) {
            return null;
        }
        return house.address.contains(address);
    }

    private BooleanExpression equalStatus(String status) {
        if (status == null || status.isEmpty()) {
            return null;
        }
        return house.status.eq(status);
    }

    private BooleanExpression betweenApplyDate(String applyStartDate, String applyEndDate) {
        if (applyStartDate == null || applyEndDate == null || applyStartDate.isEmpty() || applyEndDate.isEmpty()) {
            return null;
        }
        applyStartDate = applyStartDate.replace("-", ".");
        applyEndDate = applyEndDate.replace("-", ".");
        return house.applyStartDate.between(applyStartDate, applyEndDate);
    }
}
