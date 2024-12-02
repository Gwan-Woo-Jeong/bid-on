package com.test.bidon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.bidon.entity.ReviewPhoto;
import com.test.bidon.service.ReviewPhotoService;

@RestController
@RequestMapping("/api/review")
public class ReviewPhotoController {

    @Autowired
    private ReviewPhotoService reviewPhotoService;

    @GetMapping("/{reviewBoardId}/photos")
    public List<Map<String, Object>> getPhotosByReviewBoardId(@PathVariable Integer reviewBoardId) {
        // 데이터 조회
        List<ReviewPhoto> photos = reviewPhotoService.getPhotosByReviewBoardId(reviewBoardId);

        // JSON 형식으로 변환
        return photos.stream().map(photo -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", photo.getId());
            map.put("reviewBoardId", photo.getReviewBoardId().getId());
            map.put("path", photo.getPath());
            return map;
        }).collect(Collectors.toList());
    }
}
