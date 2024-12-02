
package com.test.bidon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.bidon.dto.ReviewBoardDetailDTO;
import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.entity.ReviewPhoto;
import com.test.bidon.repository.HashtagRepository;
import com.test.bidon.repository.ReviewBoardRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final HashtagRepository hashtagRepository;
    private final ReviewBoardRepository reviewBoardRepository;

    public ReviewBoardDetailDTO getReviewDetail(Integer id) {
        // 리뷰 데이터 조회
        ReviewBoard review = reviewBoardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));

        // 해시태그 조회
        List<String> hashTags = hashtagRepository.findHashtagsByReviewBoardId(id);

        // 리뷰 사진 조회
        List<ReviewPhoto> reviewPhotos = review.getReviewPhotos(); // Lazy Loading

        // DTO에 데이터 포함
        return ReviewBoardDetailDTO.builder()
            .review(review)
            .hashTags(hashTags)
            .reviewPhotos(reviewPhotos) // 리뷰 사진 추가
            .build();
    }
}



