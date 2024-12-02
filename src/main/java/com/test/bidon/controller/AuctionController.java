package com.test.bidon.controller;

import com.test.bidon.config.security.CustomUserDetails;
import com.test.bidon.repository.WinningBidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {
    private final WinningBidRepository winningBidRepository;

    @GetMapping("/winner")
    public String winner(Model model, String auctionType, String itemId, String winningBidId) {
        getWinningBidCount(model, auctionType, itemId, winningBidId);
        return "/user/winner";
    }

    @GetMapping("/payment")
    public String payment(Model model, String auctionType, String itemId, String winningBidId) {
        getWinningBidCount(model, auctionType, itemId, winningBidId);
        return "/user/payment";
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