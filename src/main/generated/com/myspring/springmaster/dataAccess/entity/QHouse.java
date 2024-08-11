package com.myspring.springmaster.dataAccess.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHouse is a Querydsl query type for House
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHouse extends EntityPathBase<House> {

    private static final long serialVersionUID = 999843742L;

    public static final QHouse house = new QHouse("house");

    public final StringPath address = createString("address");

    public final DateTimePath<String> applyEndDate = createDateTime("applyEndDate", String.class);

    public final DateTimePath<String> applyStartDate = createDateTime("applyStartDate", String.class);

    public final StringPath company = createString("company");

    public final ListPath<HouseDetail, QHouseDetail> houseDetails = this.<HouseDetail, QHouseDetail>createList("houseDetails", HouseDetail.class, QHouseDetail.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> latitude = createNumber("latitude", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> longitude = createNumber("longitude", java.math.BigDecimal.class);

    public final DateTimePath<String> moveInDate = createDateTime("moveInDate", String.class);

    public final StringPath name = createString("name");

    public final StringPath redirectUrlForm = createString("redirectUrlForm");

    public final StringPath status = createString("status");

    public QHouse(String variable) {
        super(House.class, forVariable(variable));
    }

    public QHouse(Path<? extends House> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHouse(PathMetadata metadata) {
        super(House.class, metadata);
    }

}

