package com.test.bidon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.test.bidon.entity.ReviewBoard;

@Repository
public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long> {

    // Fetch Join으로 연관된 UserEntity를 한 번에 로드
    @Query("SELECT rb FROM ReviewBoard rb JOIN FETCH rb.userEntityInfo")
    List<ReviewBoard> findAllWithUserEntity();
    
    
    @Query(value = "CALL AddReviewProcedure(:p_title, :p_contents, :p_email, :p_thumbnailPath, :p_additionalPhotos)", nativeQuery = true)
    void addReview(@Param("p_title") String p_title, @Param("p_contents") String p_contents, 
                   @Param("p_email") String p_email, @Param("p_thumbnailPath") String p_thumbnailPath, 
                   @Param("p_additionalPhotos") String p_additionalPhotos);
    
    @Modifying
    @Transactional
    @Query("UPDATE ReviewBoard rb SET rb.views = rb.views + 1 WHERE rb.id = :id")
    void incrementViews(@Param("id") Long id);
   
}

