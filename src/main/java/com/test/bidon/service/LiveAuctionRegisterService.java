package com.test.bidon.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.test.bidon.entity.LiveAuctionItem;
import com.test.bidon.entity.LiveAuctionItemImage;
import com.test.bidon.entity.LiveAuctionItemImageList;
import com.test.bidon.repository.LiveAuctionItemImageListRepository;
import com.test.bidon.repository.LiveAuctionItemImageRepository;
import com.test.bidon.repository.LiveAuctionItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LiveAuctionRegisterService {
	
	private final LiveAuctionItemRepository liveAuctionItemRepository;
	private final LiveAuctionItemImageRepository liveAuctionItemImageRepository;
	private final LiveAuctionItemImageListRepository liveAuctionItemImageListRepository;
	
	
	@Transactional
    public void registerLiveAuction(LiveAuctionItem liveAuctionItem, List<MultipartFile> files, List<Integer> isMainImage) {
        // Step 1.5: 경매 아이템 저장
        liveAuctionItemRepository.save(liveAuctionItem);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            Integer isRealMainImage = isMainImage.get(i);

            // Step 1: 이미지 파일 저장
            String filePath = saveFile(file);
            LiveAuctionItemImage liveAuctionItemImage = new LiveAuctionItemImage();
            liveAuctionItemImage.setPath(filePath);
            liveAuctionItemImageRepository.save(liveAuctionItemImage); // 명시적으로 저장

            // Step 2: 이미지 리스트 저장
            LiveAuctionItemImageList imageList = new LiveAuctionItemImageList();
            imageList.setLiveAuctionItem(liveAuctionItem); // LiveAuctionItem 참조
            imageList.setLiveAuctionItemImage(liveAuctionItemImage); // 저장된 LiveAuctionItemImage 참조
            imageList.setIsMainImage(isRealMainImage); // 대표 이미지 설정
            liveAuctionItemImageListRepository.save(imageList); // 저장
        }
    }

    private String saveFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return path.toString();
        } catch (Exception e) {
            throw new RuntimeException("파일 저장 실패: " + e.getMessage());
        }
    }
		
		
	}


