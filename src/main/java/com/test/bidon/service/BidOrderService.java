package com.test.bidon.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.bidon.dto.CombinedAuctionDTO;
import com.test.bidon.dto.LiveAuctionItemDTO;
import com.test.bidon.entity.LiveAuctionItem;
import com.test.bidon.entity.LiveBidCost;
import com.test.bidon.entity.NormalAuctionItem;
import com.test.bidon.entity.NormalBidInfo;
import com.test.bidon.entity.OrderInfo;
import com.test.bidon.entity.UserEntity;
import com.test.bidon.entity.WinningBid;
import com.test.bidon.repository.LiveAuctionItemRepository;
import com.test.bidon.repository.LiveBidCostRepository;
import com.test.bidon.repository.NormalAuctionItemRepository;
import com.test.bidon.repository.NormalBidInfoRepository;
import com.test.bidon.repository.OrderInfoRepository;
import com.test.bidon.repository.PaymentRepository;
import com.test.bidon.repository.UserRepository;
import com.test.bidon.repository.WinningBidRepository;


@Service
@Transactional(readOnly = true)
public class BidOrderService {
    private final WinningBidRepository winningBidRepository;
    private final OrderInfoRepository orderInfoRepository;
    private final UserRepository userRepository;
    private final NormalAuctionItemRepository normalAuctionItemRepository; 
    private final LiveBidCostRepository liveBidCostRepository;
    private final LiveAuctionItemRepository liveAuctionItemRepository;
    private final NormalBidInfoRepository normalBidInfoRepository;  // 일반 경매 입찰 정보
    //private final PaymentRepository paymentRepository;  // 결제 정보

    public BidOrderService(
    	    WinningBidRepository winningBidRepository,
    	    OrderInfoRepository orderInfoRepository,
    	    UserRepository userRepository,
    	    NormalAuctionItemRepository normalAuctionItemRepository,
    	    LiveBidCostRepository liveBidCostRepository,
    	    LiveAuctionItemRepository liveAuctionItemRepository,
    	    NormalBidInfoRepository normalBidInfoRepository,
    	    PaymentRepository paymentRepository
    	) {
    	    this.winningBidRepository = winningBidRepository;
    	    this.orderInfoRepository = orderInfoRepository;
    	    this.userRepository = userRepository;
    	    this.normalAuctionItemRepository = normalAuctionItemRepository;
    	    this.liveBidCostRepository = liveBidCostRepository;
    	    this.liveAuctionItemRepository = liveAuctionItemRepository;
    	    this.normalBidInfoRepository = normalBidInfoRepository;
    	    //this.paymentRepository = paymentRepository;
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
    
    // 1. 사용자가 입찰한 경매 목록 조회 (일반 + 실시간)
    public List<CombinedAuctionDTO> getBiddingAuctions(Long userId) {
        // 일반 경매 입찰 목록
        List<CombinedAuctionDTO> normalBids = normalBidInfoRepository.findByUserInfoId(userId)
            .stream()
            .map(bid -> {
                NormalAuctionItem item = bid.getNormalAuctionItem();
                return CombinedAuctionDTO.builder()
                    .auctionType("일반")
                    .id(item.getId())
                    .name(item.getName())
                    .currentPrice(bid.getBidPrice())
                    .startPrice(item.getStartPrice())
                    .remainingTime(calculateRemainingTime(item.getEndTime()))
                    .build();
            })
            .collect(Collectors.toList());

        // 실시간 경매 입찰 목록
        List<CombinedAuctionDTO> liveBids = liveBidCostRepository.findByLiveAuctionPartUserInfoId(userId)
            .stream()
            .map(bid -> {
                LiveAuctionItem item = bid.getLiveAuctionItem();
                return CombinedAuctionDTO.builder()
                    .auctionType("실시간")
                    .id(item.getId())
                    .name(item.getName())
                    .currentPrice(getLatestBidPrice(item.getId()))
                    .startPrice(item.getStartPrice())
                    .remainingTime(calculateRemainingTime(item.getEndTime()))
                    .build();
            })
            .collect(Collectors.toList());

        List<CombinedAuctionDTO> allBids = new ArrayList<>();
        allBids.addAll(normalBids);
        allBids.addAll(liveBids);
        return allBids;
    }

    // 2. 사용자가 낙찰받은 경매 목록 조회
    public List<CombinedAuctionDTO> getWonAuctions(Long userId) {
        List<CombinedAuctionDTO> wonAuctions = new ArrayList<>();
        
        // 일반 경매 낙찰 목록
        List<WinningBid> normalWonBids = winningBidRepository.findAllByUserInfoIdAndNormalBidIdIsNotNull(userId);
        for (WinningBid winningBid : normalWonBids) {
            normalBidInfoRepository.findById(winningBid.getNormalBidId())
                .ifPresent(normalBid -> {
                    NormalAuctionItem item = normalBid.getNormalAuctionItem();
                    if (item != null) {
                        wonAuctions.add(CombinedAuctionDTO.builder()
                            .auctionType("일반")
                            .id(item.getId())
                            .name(item.getName())
                            .finalPrice(normalBid.getBidPrice())
                            .soldDate(item.getEndTime())
                            .sellerName(item.getUserInfo() != null ? item.getUserInfo().getName() : "-")
                            .status(getPaymentStatus(winningBid.getId()))
                            .build());
                    }
                });
        }

        // 실시간 경매 낙찰 목록
        List<WinningBid> liveWonBids = winningBidRepository.findAllByUserInfoIdAndLiveBidIdIsNotNull(userId);
        for (WinningBid winningBid : liveWonBids) {
            liveBidCostRepository.findById(winningBid.getLiveBidId())
                .ifPresent(liveBidCost -> {
                    LiveAuctionItem item = liveBidCost.getLiveAuctionItem();
                    if (item != null) {
                        wonAuctions.add(CombinedAuctionDTO.builder()
                            .auctionType("실시간")
                            .id(item.getId())
                            .name(item.getName())
                            .finalPrice(liveBidCost.getBidPrice())
                            .soldDate(item.getEndTime())
                            .sellerName(item.getUserInfo() != null ? item.getUserInfo().getName() : "-")
                            .status(getPaymentStatus(winningBid.getId()))
                            .build());
                    }
                });
        }

        return wonAuctions;
    }


    // 결제 상태 조회 도우미 메서드
    private String getPaymentStatus(Long winningBidId) {
        return orderInfoRepository.findByWinningBidId(winningBidId)
            .map(order -> order.getPayment().getStatus())
            .orElse("결제 대기");
    }
    
    
    // 실시간 경매 입찰 정보 조회
    public List<LiveAuctionItemDTO> getLiveBidsByUserId(Long userId) {
        // LiveBidCost에서 사용자의 입찰 정보를 조회
        List<LiveBidCost> userBids = liveBidCostRepository.findByLiveAuctionPartUserInfoId(userId);
        
        return userBids.stream()
            .map(LiveBidCost::getLiveAuctionItem)
            .distinct()
            .map(item -> {
                Integer latestBidPrice = getLatestBidPrice(item.getId());
                
                return LiveAuctionItemDTO.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .startPrice(item.getStartPrice())
                    .finalPrice(latestBidPrice)
                    .startTime(item.getStartTime())
                    .endTime(item.getEndTime())
                    // 판매자 정보도 포함
                    .userInfoId(item.getUserInfoId())  // 판매자 ID
                    .userInfo(item.getUserInfo().toDTO())  // 판매자 정보
                    .build();
            })
            .collect(Collectors.toList());
    }

    // 낙찰된 실시간 경매 조회
    public List<LiveAuctionItemDTO> getWonLiveAuctions(Long userId) {
        // 사용자가 낙찰받은 실시간 경매 항목 조회
        return winningBidRepository.findByUserInfoIdAndLiveBidIdIsNotNull(userId).stream()
            .map(wb -> liveAuctionItemRepository.findById(wb.getLiveBidId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(item -> {
                Integer finalBidPrice = getLatestBidPrice(item.getId());
                
                return LiveAuctionItemDTO.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .startPrice(item.getStartPrice())
                    .finalPrice(finalBidPrice)
                    .startTime(item.getStartTime())
                    .endTime(item.getEndTime())
                    .userInfoId(item.getUserInfoId())  // 판매자 ID
                    .userInfo(item.getUserInfo().toDTO())  // 판매자 정보
                    .build();
            })
            .collect(Collectors.toList());
    }
    
    public Integer getLatestBidPrice(Long itemId) {
        return liveBidCostRepository.findTopByLiveAuctionItemIdOrderByBidTimeDesc(itemId)
            .map(LiveBidCost::getBidPrice)
            .orElse(null);
    }
    
    // 판매된 경매 물품 조회 메서드 수정
    public List<CombinedAuctionDTO> getSoldAuctionsByUserId(Long userId) {
        List<CombinedAuctionDTO> allAuctions = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        // 일반 경매 물품 목록
        normalAuctionItemRepository.findByUserInfoId(userId)
            .forEach(item -> {
                String sellStatus;
                if (item.getEndTime() == null || item.getEndTime().isAfter(now)) {
                    sellStatus = "진행중";
                } else {
                    sellStatus = "종료";
                }
                
                UserEntity buyer = null;
                if (sellStatus.equals("종료")) {
                    // 수정된 구매자 조회 로직
                    Optional<NormalBidInfo> lastBid = normalBidInfoRepository
                        .findTopByAuctionItemIdOrderByBidDateDesc(item.getId());
                    
                    if (lastBid.isPresent()) {
                        buyer = winningBidRepository.findByNormalBidId(lastBid.get().getId())
                            .map(winningBid -> userRepository.findById(winningBid.getUserInfoId())
                                .orElse(null))
                            .orElse(null);
                    }
                }

                allAuctions.add(CombinedAuctionDTO.builder()
                    .auctionType("일반")
                    .id(item.getId())
                    .name(item.getName())
                    .startPrice(item.getStartPrice())
                    .finalPrice(item.getEndTime() != null && item.getEndTime().isBefore(now) ? 
                        normalBidInfoRepository.findTopByAuctionItemIdOrderByBidDateDesc(item.getId())
                            .map(NormalBidInfo::getBidPrice)
                            .orElse(item.getStartPrice()) : item.getStartPrice())
                    .soldDate(item.getEndTime())
                    .sellStatus(sellStatus)
                    .buyerName(buyer != null ? buyer.getName() : "-")
                    .build());
            });

        // 실시간 경매 물품 목록
        liveAuctionItemRepository.findByUserInfoId(userId)
            .forEach(item -> {
                String sellStatus;
                if (item.getEndTime() == null || item.getEndTime().isAfter(now)) {
                    sellStatus = "진행중";
                } else {
                    sellStatus = "종료";
                }
                
                UserEntity buyer = null;
                if (sellStatus.equals("종료")) {
                    // 수정된 구매자 조회 로직
                    Optional<LiveBidCost> lastBid = liveBidCostRepository
                        .findTopByLiveAuctionItemIdOrderByBidTimeDesc(item.getId());
                    
                    if (lastBid.isPresent()) {
                        buyer = winningBidRepository.findByLiveBidId(lastBid.get().getId())
                            .map(winningBid -> userRepository.findById(winningBid.getUserInfoId())
                                .orElse(null))
                            .orElse(null);
                    }
                }

                allAuctions.add(CombinedAuctionDTO.builder()
                    .auctionType("실시간")
                    .id(item.getId())
                    .name(item.getName())
                    .startPrice(item.getStartPrice())
                    .finalPrice(getLatestBidPrice(item.getId()))
                    .soldDate(item.getEndTime())
                    .sellStatus(sellStatus)
                    .buyerName(buyer != null ? buyer.getName() : "-")
                    .build());
            });

        return allAuctions;
    }

    // 남은 시간 계산 도우미 메서드
    private String calculateRemainingTime(LocalDateTime endTime) {
        if (endTime == null) {
            return "-";
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (endTime.isBefore(now)) {
            return "종료";
        }

        Duration duration = Duration.between(now, endTime);
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        return String.format("%d일 %02d:%02d:%02d", days, hours, minutes, seconds);
    }
    
    // 사용자의 경매 활동 수 조회 (입찰한 일반 경매 + 실시간 경매 수)
    public int countBiddingActivities(Long userId) {
        // 일반 경매 입찰 수
        int normalBidCount = normalBidInfoRepository.countByUserInfoId(userId);
        
        // 실시간 경매 입찰 수
        int liveBidCount = liveBidCostRepository.countByLiveAuctionPartUserInfoId(userId);
        
        return normalBidCount + liveBidCount;
    }

    // 사용자의 낙찰 횟수 조회 (일반 + 실시간 경매)
    public int countWonAuctions(Long userId) {
        // 일반 경매 낙찰 수
        int normalWonCount = winningBidRepository.countByUserInfoIdAndNormalBidIdIsNotNull(userId);
        
        // 실시간 경매 낙찰 수
        int liveWonCount = winningBidRepository.countByUserInfoIdAndLiveBidIdIsNotNull(userId);
        
        return normalWonCount + liveWonCount;
    }

    // 사용자의 판매 활동 수 조회 (판매 등록한 일반 + 실시간 경매 수)
    public int countSellingActivities(Long userId) {
        // 일반 경매 판매 수
        int normalSellCount = normalAuctionItemRepository.countByUserInfoId(userId);
        
        // 실시간 경매 판매 수
        int liveSellCount = liveAuctionItemRepository.countByUserInfoId(userId);
        
        return normalSellCount + liveSellCount;
    }
    
}
