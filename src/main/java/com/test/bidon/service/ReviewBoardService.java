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
    
    @Autowired
    private FileService fileService;
    

    // ReviewBoardId로 ReviewBoard 데이터 가져오기
    public ReviewBoard getReviewBoardById(Integer reviewBoardId) {
        return reviewBoardRepository.findById(reviewBoardId).orElse(null);
    }

	public ReviewBoard findById(Integer reviewBoardId) {
		
		return null;
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
//    public void deleteReview(Integer reviewBoardId) {
//        ReviewBoard reviewBoard = reviewBoardRepository.findById(reviewBoardId)
//                .orElseThrow(() -> new RuntimeException("삭제하려는 게시글을 찾을 수 없습니다."));
//
//        // 파일 삭제 처리 (썸네일 및 추가 사진)
//        if (reviewBoard.getThumbnailPath() != null) {
//            fileService.deleteFile(reviewBoard.getThumbnailPath());
//        }
//
//        if (reviewBoard.getAdditionalPhotos() != null) {
//            for (String photoPath : reviewBoard.getAdditionalPhotos().split(",")) {
//                fileService.deleteFile(photoPath);
//            }
//        }
//    
//        reviewBoardRepository.delete(reviewBoard);
//    }
	
}
