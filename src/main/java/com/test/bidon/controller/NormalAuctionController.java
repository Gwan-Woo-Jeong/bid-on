package com.test.bidon.controller;

import com.test.bidon.repository.CustomNormalAuctionItemRepository;
import com.test.bidon.repository.NormalAuctionItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NormalAuctionController {

    private final NormalAuctionItemRepository normalAuctionItemRepository;
    private final CustomNormalAuctionItemRepository customNormalAuctionItemRepository;

}
