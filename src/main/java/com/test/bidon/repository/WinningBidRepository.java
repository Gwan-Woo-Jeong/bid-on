package com.test.bidon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.bidon.entity.WinningBid;

@Repository
public interface WinningBidRepository extends JpaRepository<WinningBid, Long> {
    Optional<WinningBid> findByNormalBidId(Long normalBidId);
}
