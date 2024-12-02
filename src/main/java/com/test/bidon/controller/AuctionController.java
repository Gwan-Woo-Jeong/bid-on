package com.test.bidon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.bidon.config.security.CustomUserDetails;
import com.test.bidon.dto.CheckoutItemDTO;
import com.test.bidon.dto.OrderInfoDTO;
import com.test.bidon.dto.OrderItemDTO;
import com.test.bidon.dto.PaymentDTO;
import com.test.bidon.entity.*;
import com.test.bidon.repository.*;
import com.test.bidon.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {
    private final WinningBidRepository winningBidRepository;
    private final CustomLiveAuctionItemRepository customLiveAuctionItemRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final OrderInfoRepository orderInfoRepository;
    private final PaymentRepository paymentRepository;
    private final CustomOrderInfoRepository customOrderInfoRepository;

    @GetMapping("/winner")
    public String winner(Model model, String auctionType, String itemId, String winningBidId) {
        getWinningBidCount(model, auctionType, itemId, winningBidId);

        return "/user/winner";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, String auctionType, String itemId, String winningBidId) {
        getWinningBidCount(model, auctionType, itemId, winningBidId);
        getAuctionItemInfo(model, auctionType, itemId, winningBidId);
        List<DeliveryMethod> deliveryMethods = deliveryMethodRepository.findAll();
        model.addAttribute("deliveryMethods", deliveryMethods);

        return "user/checkout";
    }

    @GetMapping("/payment")
    public String payment(Model model, String auctionType, String itemId, String winningBidId) throws JsonProcessingException {
        getWinningBidCount(model, auctionType, itemId, winningBidId);
        getAuctionItemInfo(model, auctionType, itemId, winningBidId);
        List<DeliveryMethod> deliveryMethods = deliveryMethodRepository.findAll();

        model.addAttribute("deliveryMethods", deliveryMethods);

        return "user/payment";
    }

    @PostMapping("/order-ok")
    @ResponseBody
    public ResponseEntity<Map<String, String>> orderOk(
            @RequestPart(name = "orderInfo") OrderInfoDTO orderInfoDTO,
            @RequestPart(name = "payment") PaymentDTO paymentDTO
    ) {
        try {

            OrderInfo savedOrder = orderInfoRepository.save(orderInfoDTO.toEntity());
            paymentDTO.setOrderId(savedOrder.getId());
            paymentRepository.save(paymentDTO.toEntity());

            String data = JsonUtil.toJson(savedOrder);

            return ResponseEntity.ok(Map.of("message", "결제가 완료되었습니다.", "data", data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "결제 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @GetMapping("/thankyou")
    public String thankyou(Model model, String orderId, String auctionType) {
        if (auctionType.equals("live")) {
            OrderItemDTO item = customOrderInfoRepository.getOrderInfoWithAuctionItem(Long.valueOf(orderId));
            model.addAttribute("item", item);
        } else if (auctionType.equals("normal")) {
            // TODO: 일반 경매
        }

        return "user/thankyou";
    }

    private void getAuctionItemInfo(Model model, String auctionType, String itemId, String winningBidId) {
        if (auctionType.equals("live")) {
            CheckoutItemDTO item = customLiveAuctionItemRepository.getCheckoutItem(Long.parseLong(itemId), Long.parseLong(winningBidId));
            model.addAttribute("item", item);
        } else if (auctionType.equals("normal")) {
            // TODO: 일반 경매
        }
    }

    private void getWinningBidCount(Model model, String auctionType, String itemId, String winningBidId) {
        Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        int winningBidCount = 0;

        if (auctionType.equals("live")) {
            winningBidCount = winningBidRepository.countByWinBidIdAndLiveItemId(Long.parseLong(winningBidId), Long.parseLong(itemId), userId);
        } else if (auctionType.equals("normal")) {
            winningBidCount = winningBidRepository.countByWinBidIdAndNormalItemId(Long.parseLong(winningBidId), Long.parseLong(itemId), userId);
        }

        model.addAttribute("winningBidCount", winningBidCount);
    }
}