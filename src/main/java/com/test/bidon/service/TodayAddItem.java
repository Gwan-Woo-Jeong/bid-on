package com.test.bidon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.test.bidon.entity.LiveAuctionItem;
import com.test.bidon.entity.NormalAuctionItem;

@Service
public class TodayAddItem {
	
	public List<NormalAuctionItem> TodayAddNormalItem(List<NormalAuctionItem> items, LocalDateTime currentTime) {
		return items.stream()
                .filter(item -> item.getStartTime().toLocalDate().isEqual(currentTime.toLocalDate())) // 오늘 등록된 아이템만
                .collect(Collectors.toList());
	}
	
	public List<LiveAuctionItem> TodayAddLiveItems(List<LiveAuctionItem> items, LocalDateTime currentTime) {
        return items.stream()
                .filter(item -> item.getCreateTime().toLocalDate().isEqual(currentTime.toLocalDate())) // 오늘 등록된 아이템만
                .collect(Collectors.toList());
    }

}
