package com.test.bidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.repository.ReviewBoardRepository;

@Service
public class ReviewBoardService {

    @Autowired
    private ReviewBoardRepository reviewBoardRepository;

    // ReviewBoardId로 ReviewBoard 데이터 가져오기
    public ReviewBoard getReviewBoardById(Integer reviewBoardId) {
        return reviewBoardRepository.findById(reviewBoardId).orElse(null);
    }

	public ReviewBoard findById(Integer reviewBoardId) {
		
		return null;
	}
}
