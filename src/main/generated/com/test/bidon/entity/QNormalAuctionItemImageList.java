package com.test.bidon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNormalAuctionItemImageList is a Querydsl query type for NormalAuctionItemImageList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNormalAuctionItemImageList extends EntityPathBase<NormalAuctionItemImageList> {

    private static final long serialVersionUID = -881074700L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNormalAuctionItemImageList normalAuctionItemImageList = new QNormalAuctionItemImageList("normalAuctionItemImageList");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isMainImage = createBoolean("isMainImage");

    public final QNormalAuctionItem normalAuctionItem;

    public final QNormalAuctionItemImage normalAuctionItemImage;

    public QNormalAuctionItemImageList(String variable) {
        this(NormalAuctionItemImageList.class, forVariable(variable), INITS);
    }

    public QNormalAuctionItemImageList(Path<? extends NormalAuctionItemImageList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNormalAuctionItemImageList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNormalAuctionItemImageList(PathMetadata metadata, PathInits inits) {
        this(NormalAuctionItemImageList.class, metadata, inits);
    }

    public QNormalAuctionItemImageList(Class<? extends NormalAuctionItemImageList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.normalAuctionItem = inits.isInitialized("normalAuctionItem") ? new QNormalAuctionItem(forProperty("normalAuctionItem"), inits.get("normalAuctionItem")) : null;
        this.normalAuctionItemImage = inits.isInitialized("normalAuctionItemImage") ? new QNormalAuctionItemImage(forProperty("normalAuctionItemImage")) : null;
    }

}

