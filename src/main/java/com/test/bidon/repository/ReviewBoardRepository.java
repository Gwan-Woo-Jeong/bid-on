package com.test.bidon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.test.bidon.entity.ReviewBoard;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Integer> {
    // 페이징 처리를 위한 메서드
    Page<ReviewBoard> findAll(Pageable pageable);
}
