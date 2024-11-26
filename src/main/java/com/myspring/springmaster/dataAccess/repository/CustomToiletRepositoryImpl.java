package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.dataAccess.entity.Toilet;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.myspring.springmaster.dataAccess.entity.QToilet.toilet;

@Repository
public class CustomToiletRepositoryImpl implements CustomToiletRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public CustomToiletRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Toilet> findAllByFilter(ToiletDTO filter) {
        return jpaQueryFactory
                .selectFrom(toilet)
                .where(
                        containsAddress(filter.getAddress()),
                        equalEmergencyBellInstalled(filter.getEmergency_bell_installed()),
                        equalEntranceCctvInstalled(filter.getEntrance_cctv_installed()),
                        equalDiaperChangingStation(filter.getDiaper_changing_station())
                )
                .fetch();
    }

    private BooleanExpression containsAddress(String address) {
        if (address == null || address.isEmpty()) {
            return null;
        }
        return toilet.address.eq(address);
    }


    private BooleanExpression equalEmergencyBellInstalled(Boolean emergencyBellInstalled) {
        if (emergencyBellInstalled == null) {
            return null;
        }
        return toilet.emergency_bell_installed.eq(emergencyBellInstalled);
    }

    private BooleanExpression equalEntranceCctvInstalled(Boolean entranceCctvInstalled) {
        if (entranceCctvInstalled == null) {
            return null;
        }
        return toilet.entrance_cctv_installed.eq(entranceCctvInstalled);
    }

    private BooleanExpression equalDiaperChangingStation(Boolean diaperChangingStation) {
        if (diaperChangingStation == null) {
            return null;
        }
        return toilet.diaper_changing_station.eq(diaperChangingStation);
    }
}