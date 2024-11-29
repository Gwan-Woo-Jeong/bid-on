package com.test.bidon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNormalAuctionItem is a Querydsl query type for NormalAuctionItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNormalAuctionItem extends EntityPathBase<NormalAuctionItem> {

    private static final long serialVersionUID = -11221979L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNormalAuctionItem normalAuctionItem = new QNormalAuctionItem("normalAuctionItem");

    public final NumberPath<Long> categorySubId = createNumber("categorySubId", Long.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> startPrice = createNumber("startPrice", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final StringPath status = createString("status");

    public final QUserEntity userInfo;

    public final NumberPath<Long> userInfoId = createNumber("userInfoId", Long.class);

    public QNormalAuctionItem(String variable) {
        this(NormalAuctionItem.class, forVariable(variable), INITS);
    }

    public QNormalAuctionItem(Path<? extends NormalAuctionItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNormalAuctionItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNormalAuctionItem(PathMetadata metadata, PathInits inits) {
        this(NormalAuctionItem.class, metadata, inits);
    }

    public QNormalAuctionItem(Class<? extends NormalAuctionItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userInfo = inits.isInitialized("userInfo") ? new QUserEntity(forProperty("userInfo")) : null;
    }

}

