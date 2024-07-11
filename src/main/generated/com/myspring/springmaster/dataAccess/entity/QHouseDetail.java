package com.myspring.springmaster.dataAccess.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHouseDetail is a Querydsl query type for HouseDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHouseDetail extends EntityPathBase<HouseDetail> {

    private static final long serialVersionUID = -346636913L;

    public static final QHouseDetail houseDetail = new QHouseDetail("houseDetail");

    public final StringPath deposit = createString("deposit");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath monthlyRent = createString("monthlyRent");

    public final StringPath size = createString("size");

    public final NumberPath<Integer> supplyCount = createNumber("supplyCount", Integer.class);

    public final StringPath type = createString("type");

    public QHouseDetail(String variable) {
        super(HouseDetail.class, forVariable(variable));
    }

    public QHouseDetail(Path<? extends HouseDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHouseDetail(PathMetadata metadata) {
        super(HouseDetail.class, metadata);
    }

}

