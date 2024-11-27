package com.test.bidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.bidon.entity.ReviewPhoto;
import com.test.bidon.repository.ReviewPhotoRepository;

@Service
public class ReviewPhotoService {

    @Autowired
    private ReviewPhotoRepository reviewPhotoRepository;

    public List<ReviewPhoto> getPhotosByReviewBoardId(Integer reviewBoardId) {
        // ReviewBoardId를 기반으로 검색
        return reviewPhotoRepository.findByReviewBoardId(reviewBoardId);
    }
}


