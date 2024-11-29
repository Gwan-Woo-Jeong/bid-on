package com.test.bidon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNormalAuctionItemImage is a Querydsl query type for NormalAuctionItemImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNormalAuctionItemImage extends EntityPathBase<NormalAuctionItemImage> {

    private static final long serialVersionUID = 778093622L;

    public static final QNormalAuctionItemImage normalAuctionItemImage = new QNormalAuctionItemImage("normalAuctionItemImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath path = createString("path");

    public QNormalAuctionItemImage(String variable) {
        super(NormalAuctionItemImage.class, forVariable(variable));
    }

    public QNormalAuctionItemImage(Path<? extends NormalAuctionItemImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNormalAuctionItemImage(PathMetadata metadata) {
        super(NormalAuctionItemImage.class, metadata);
    }

}

