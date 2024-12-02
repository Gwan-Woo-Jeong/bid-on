package com.test.bidon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.entity.ReviewPhoto;
import com.test.bidon.service.ReviewBoardService;
import com.test.bidon.service.ReviewPhotoService;

@Controller
public class ReviewPhotoController {

//    @Autowired
//    private ReviewPhotoService reviewPhotoService;
//
//    @Autowired
//    private ReviewBoardService reviewBoardService;
//
//    @GetMapping("/blog-detail")
//    public String showBlogDetailPage(
//            @RequestParam("reviewBoardId") Integer reviewBoardId, Model model) {
//
//
//        // reviewBoardId를 기반으로 데이터 조회
//        ReviewBoard reviewBoard = reviewBoardService.findById(reviewBoardId);
//
//        // 데이터가 없을 경우 처리
//        if (reviewBoard == null) {
//            throw new IllegalArgumentException("Invalid reviewBoardId: " + reviewBoardId);
//        }
//        
//        // ReviewPhoto 데이터 가져오기
//        List<ReviewPhoto> photos = reviewPhotoService.getPhotosByReviewBoardId(reviewBoardId);
//
//        // 모델에 데이터 추가
//        model.addAttribute("reviewBoard", reviewBoard);
//        model.addAttribute("photos", photos);
//
//        return "blog-detail";
//    }
}
