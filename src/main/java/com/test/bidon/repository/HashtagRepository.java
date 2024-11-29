package com.test.bidon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.HashTagEntity;
import com.test.bidon.entity.HashTaggingEntity;

@Repository
public interface HashtagRepository extends JpaRepository<HashTagEntity, Long> {

    @Query("SELECT h.tag FROM HashTaggingEntity hg " +
           "INNER JOIN hg.hashTag h " + 
           "WHERE hg.reviewBoard.id = :reviewBoardId")
    List<String> findHashtagsByReviewBoardId(@Param("reviewBoardId") Long reviewBoardId);
}
