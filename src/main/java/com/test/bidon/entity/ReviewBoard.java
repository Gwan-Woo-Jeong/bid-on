//package com.test.bidon.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "ReviewBoard")
//@Data
//public class ReviewBoard {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqreviewboard")
//    @SequenceGenerator(name = "seqreviewboard", sequenceName = "seqReviewBoard", allocationSize = 1)
//    private Integer id; // 후기게시판의 고유 SEQ 번호
//    
//    @ManyToOne
//    @JoinColumn(name = "USERINFOID", nullable = false) // 사용자번호 외래 키
//    private UserEntity userEntityInfo;
//
//    @Column(nullable = false, length = 100)
//    private String title; // 제목
//
//    @Column(nullable = false, length = 3000)
//    private String contents; // 내용
//
//    @Column(nullable = false)
//    private Integer views = 0; // 조회수 (기본값 0)
//
//    @Column(nullable = false)
//    private LocalDate regdate; // 작성 시간
//
//    @PrePersist
//    public void prePersist() {
//        if (this.regdate == null) {
//            this.regdate = LocalDate.now(); // 작성 시간 자동 설정
//        }
//    }
//}
