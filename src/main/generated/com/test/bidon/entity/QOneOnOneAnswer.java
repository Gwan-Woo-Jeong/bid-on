package com.test.bidon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOneOnOneAnswer is a Querydsl query type for OneOnOneAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOneOnOneAnswer extends EntityPathBase<OneOnOneAnswer> {

    private static final long serialVersionUID = -1796424567L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOneOnOneAnswer oneOnOneAnswer = new QOneOnOneAnswer("oneOnOneAnswer");

    public final StringPath answer = createString("answer");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DatePath<java.time.LocalDate> regdate = createDate("regdate", java.time.LocalDate.class);

    public final QOneOnOne seqOneOnOne;

    public QOneOnOneAnswer(String variable) {
        this(OneOnOneAnswer.class, forVariable(variable), INITS);
    }

    public QOneOnOneAnswer(Path<? extends OneOnOneAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOneOnOneAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOneOnOneAnswer(PathMetadata metadata, PathInits inits) {
        this(OneOnOneAnswer.class, metadata, inits);
    }

    public QOneOnOneAnswer(Class<? extends OneOnOneAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.seqOneOnOne = inits.isInitialized("seqOneOnOne") ? new QOneOnOne(forProperty("seqOneOnOne"), inits.get("seqOneOnOne")) : null;
    }

}

