package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.dataAccess.entity.Toilet;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Toilet> findAllByFilter(ToiletDTO filter, Pageable pageable) {
        List<Toilet> results = jpaQueryFactory
                .selectFrom(toilet)
                .where(
                        containsAddress(filter.getAddress()),
                        equalEmergencyBellInstalled(filter.getEmergency_bell_installed()),
                        equalEntranceCctvInstalled(filter.getEntrance_cctv_installed()),
                        equalDiaperChangingStation(filter.getDiaper_changing_station())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 디버깅용 로그
        System.out.println("Filtered Results: " + results);

        long total = jpaQueryFactory
                .select(toilet.count())
                .from(toilet)
                .where(
                        containsAddress(filter.getAddress()),
                        equalEmergencyBellInstalled(filter.getEmergency_bell_installed()),
                        equalEntranceCctvInstalled(filter.getEntrance_cctv_installed()),
                        equalDiaperChangingStation(filter.getDiaper_changing_station())
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }


    private BooleanExpression containsAddress(String address) {
        if (address == null || address.isEmpty()) {
            return null;
        }
        return toilet.address.containsIgnoreCase(address);
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
