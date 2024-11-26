package com.test.bidon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNormalAuctionItem is a Querydsl query type for NormalAuctionItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNormalAuctionItem extends EntityPathBase<NormalAuctionItem> {

    private static final long serialVersionUID = -11221979L;

    public static final QNormalAuctionItem normalAuctionItem = new QNormalAuctionItem("normalAuctionItem");

    public final NumberPath<Long> categorySubId = createNumber("categorySubId", Long.class);

    public final StringPath description = createString("description");

    public final StringPath endTime = createString("endTime");

    public final StringPath name = createString("name");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath startPrice = createString("startPrice");

    public final StringPath startTime = createString("startTime");

    public final StringPath status = createString("status");

    public final NumberPath<Long> userInfoId = createNumber("userInfoId", Long.class);

    public QNormalAuctionItem(String variable) {
        super(NormalAuctionItem.class, forVariable(variable));
    }

    public QNormalAuctionItem(Path<? extends NormalAuctionItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNormalAuctionItem(PathMetadata metadata) {
        super(NormalAuctionItem.class, metadata);
    }

}

