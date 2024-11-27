package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.bidon.entity.ReviewPhoto;
import com.test.bidon.entity.ReviewBoard;
import java.util.List;


public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Integer> {
    @Query("SELECT rp FROM ReviewPhoto rp WHERE rp.reviewBoardId.id = :reviewBoardId")
    List<ReviewPhoto> findByReviewBoardId(@Param("reviewBoardId") Integer reviewBoardId);
}


