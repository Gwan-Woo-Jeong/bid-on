package com.test.bidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.bidon.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // 주문 ID로 결제 정보 조회
    Optional<Payment> findByOrderId(Long orderId);
    
    // 결제 상태로 결제 정보 조회
    List<Payment> findByStatus(String status);
}