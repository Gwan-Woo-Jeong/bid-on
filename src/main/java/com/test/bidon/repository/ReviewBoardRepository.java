package com.test.bidon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.ReviewBoard;

@Repository
public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Integer> {

    // Fetch Join으로 연관된 UserEntity를 한 번에 로드
    @Query("SELECT rb FROM ReviewBoard rb JOIN FETCH rb.userEntityInfo")
    List<ReviewBoard> findAllWithUserEntity();
}
