package com.test.bidon.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.bidon.dto.ReviewBoardDetailDTO;
import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.repository.HashtagRepository;
import com.test.bidon.repository.ReviewBoardRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final HashtagRepository hashtagRepository;
    private final ReviewBoardRepository reviewBoardRepository;

    public ReviewBoardDetailDTO getReviewDetail(Long id) {
        ReviewBoard review = reviewBoardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));
        
        List<String> hashTags = hashtagRepository.findHashtagsByReviewBoardId(id);
        
        return ReviewBoardDetailDTO.builder()
            .review(review)
            .hashTags(hashTags)
            .build();
    }
}

