package com.test.bidon.repository;

import java.time.LocalDate;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.test.bidon.dto.NormalBidInfoDTO;

public class QNormalBidInfoDTO extends ConstructorExpression<NormalBidInfoDTO> {

    private static final long serialVersionUID = 1L;

    public QNormalBidInfoDTO(Expression<Long> id, 
								Expression<Long> auctionItemId, 
								Expression<Long> userInfoId, 
								Expression<Integer> bidPrice, 
								Expression<LocalDate> bidDate,
								
								Expression<String> name, 
								Expression<String> description,
								Expression<String> userName, 
								Expression<String> email,
								Expression<String> national,
								Expression<LocalDate> createDate
								
								) {
        super(NormalBidInfoDTO.class, new Expression<?>[] {
                id, auctionItemId, userInfoId, bidPrice, bidDate, name, description, userName, email, national, createDate
        });
    }
}
