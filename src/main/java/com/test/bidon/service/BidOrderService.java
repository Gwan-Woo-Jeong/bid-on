package com.test.bidon.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.bidon.entity.OrderInfo;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.entity.WinningBid;
import com.test.bidon.repository.OrderInfoRepository;
import com.test.bidon.repository.UserRepository;
import com.test.bidon.repository.WinningBidRepository;


@Service
@Transactional(readOnly = true)
public class BidOrderService {
    private final WinningBidRepository winningBidRepository;
    private final OrderInfoRepository orderInfoRepository;
    private final UserRepository userRepository;

    public BidOrderService(WinningBidRepository winningBidRepository, 
                         OrderInfoRepository orderInfoRepository,
                         UserRepository userRepository) {
        this.winningBidRepository = winningBidRepository;
        this.orderInfoRepository = orderInfoRepository;
        this.userRepository = userRepository;
    }

 // 공통 로직: normalBidId로 WinningBid 찾기
    private Optional<WinningBid> getWinningBidByNormalBidId(Long normalBidId) {
        return winningBidRepository.findByNormalBidId(normalBidId);
    }

    // 최종 주문 금액 조회
    public Integer getFinalPriceByNormalBidId(Long normalBidId) {
        return getOrderInfoByNormalBidId(normalBidId)
            .map(OrderInfo::getFinalPrice)
            .orElse(null);
    }

    // 주문 정보 전체 조회
    public Optional<OrderInfo> getOrderInfoByNormalBidId(Long normalBidId) {
        return getWinningBidByNormalBidId(normalBidId)
            .flatMap(winningBid -> orderInfoRepository.findByWinningBidId(winningBid.getId()));
    }

    // 여러 건의 주문 정보 조회
    public List<OrderInfo> getOrderInfosByNormalBidIds(List<Long> normalBidIds) {
        return normalBidIds.stream()
            .map(this::getOrderInfoByNormalBidId)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    // 구매자 이름 조회
    public String getBuyerNameByNormalBidId(Long normalBidId) {
        return getWinningBidByNormalBidId(normalBidId)
            .map(WinningBid::getUserInfoId)
            .flatMap(userRepository::findById)
            .map(UserEntity::getName)
            .orElse("-");
    }
    
}
