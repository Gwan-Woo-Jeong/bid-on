package com.test.bidon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.bidon.dto.ReviewBoardFormDTO;
import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.entity.ReviewPhoto;
import com.test.bidon.repository.ReviewBoardRepository;
import com.test.bidon.service.FileService;
import com.test.bidon.service.ReviewBoardService;

@Controller
public class ReviewBoardController {

    @Autowired
    private ReviewBoardRepository reviewBoardRepository;

    @Autowired
    private ReviewBoardService reviewBoardService;
    
    @Autowired
    private FileService fileService;
    /**
     * 블로그 페이지 - 페이징 처리
     */
    @GetMapping("/blog")
    public String getBlogPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        int pageSize = 6; // 한 페이지에 보여줄 게시글 수
        Page<ReviewBoard> reviews = reviewBoardRepository.findAll(PageRequest.of(page, pageSize));

        // ID와 사진 번호 매핑 (기존 하드코딩된 부분)
        Map<Integer, Integer> idToPhotoMap = Map.ofEntries(
            Map.entry(1, 1),
            Map.entry(2, 3),
            Map.entry(3, 5),
            Map.entry(4, 8),
            Map.entry(5, 10),
            Map.entry(6, 11),
            Map.entry(7, 12),
            Map.entry(8, 13),
            Map.entry(9, 15),
            Map.entry(10, 16),
            Map.entry(11, 18),
            Map.entry(12, 19)
        );

        // 리뷰 데이터를 가공하여 대표사진 경로 추가
        List<Map<String, Object>> reviewList = reviews.getContent().stream().map(review -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", review.getId());
            map.put("title", review.getTitle());
            map.put("contents", review.getContents());
            map.put("views", review.getViews());
            map.put("name", review.getUserEntityInfo().getName());

            // 1. 하드코딩된 데이터 우선 적용
            if (idToPhotoMap.containsKey(review.getId())) {
                int photoNumber = idToPhotoMap.get(review.getId());
                String thumbnailPath = "/user/images/review/reviewPhoto" + String.format("%03d", photoNumber) + ".png";
                map.put("thumbnailPath", thumbnailPath);
            } 
            // 2. 하드코딩되지 않은 새로운 게시글의 대표 사진 처리
            else {
                String thumbnailPath = getDynamicThumbnailPath(review);
                map.put("thumbnailPath", thumbnailPath);
            }

            return map;
        }).collect(Collectors.toList());

        // 뷰로 데이터 전달
        model.addAttribute("reviews", reviewList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reviews.getTotalPages());
        return "user/blog";
    }

    /**
     * 새로운 게시글에 대한 대표 사진 경로를 가져오는 메서드
     */
    private String getDynamicThumbnailPath(ReviewBoard review) {
        // 데이터베이스에서 대표 사진 가져오기
        ReviewPhoto mainPhoto = review.getReviewPhotos().stream()
            .findFirst() // 첫 번째 사진을 대표 사진으로 사용
            .orElse(null);

        // 대표 사진 경로 설정
        if (mainPhoto != null) {
            return mainPhoto.getPath(); // ReviewPhoto 테이블의 경로
        } else {
            return "/user/images/default_thumbnail.png"; // 기본 경로
        }
    }


    
    @GetMapping("/add-review")
    public String showAddReviewPage(Model model) {
        return "user/add-review"; // 폼 렌더링
    }

    @PostMapping("/add-review")
    public String addReview(@ModelAttribute ReviewBoardFormDTO form, Model model) {
        try {
            // 대표 사진 저장
            String thumbnailPath = null;
            if (form.getThumbnail() != null && !form.getThumbnail().isEmpty()) {
                thumbnailPath = fileService.saveFile(form.getThumbnail(), "review");
                if (thumbnailPath == null) {
                    throw new RuntimeException("대표 이미지를 저장하는 데 실패했습니다.");
                }
            } else {
                throw new RuntimeException("대표 이미지가 비어있거나 업로드되지 않았습니다.");
            }

            // 추가 사진 저장
            List<String> additionalPhotoPaths = form.getPhotos() != null
                ? form.getPhotos().stream()
                    .filter(photo -> photo != null && !photo.isEmpty())
                    .map(photo -> fileService.saveFile(photo, "review"))
                    .collect(Collectors.toList())
                : new ArrayList<>();

            // 서비스 호출
            reviewBoardService.addReview(
                form.getTitle(),
                form.getContents(),
                form.getEmail(),
                thumbnailPath,
                String.join(",", additionalPhotoPaths)
            );

            return "redirect:/blog"; // 성공 시 블로그로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "게시글 작성 중 오류가 발생했습니다: " + e.getMessage());
            return "user/add-review"; // 에러 시 폼 페이지로 다시 렌더링
        }
    }



    
    public void addReviewFromController() {
        reviewBoardService.addReview("Title", "Content", "email@example.com", "/path/to/thumbnail", "/path/to/photos");
    }


    
   
    
}