package com.test.bidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.repository.ReviewBoardRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;

@Service
public class ReviewBoardService {

    @Autowired
    private ReviewBoardRepository reviewBoardRepository;

    // ReviewBoardId로 ReviewBoard 데이터 가져오기
    public ReviewBoard getReviewBoardById(Long reviewBoardId) {
        return reviewBoardRepository.findById(reviewBoardId).orElse(null);
    }

	
	
	@PersistenceContext
    private EntityManager entityManager;

    public void addReview(String title, String contents, String email, String thumbnailPath, String additionalPhotos) {
        entityManager.createStoredProcedureQuery("AddReviewProcedure")
            .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
            .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
            .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
            .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
            .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
            .setParameter(1, title)
            .setParameter(2, contents)
            .setParameter(3, email)
            .setParameter(4, thumbnailPath) // 대표사진 경로
            .setParameter(5, additionalPhotos) // 추가사진 경로 (쉼표 구분)
            .execute();
    }
	
	
}
