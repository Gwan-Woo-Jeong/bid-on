package com.test.bidon.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ReviewBoard")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 증가
    private Integer id; // 후기게시판의 고유 ID

    @ManyToOne(fetch = FetchType.LAZY) // 필요 시 데이터 로드 (Lazy Loading)
    @JoinColumn(name = "USERINFOID", nullable = false) // UserEntity와 매핑
    private UserEntity userEntityInfo;

    @Column(nullable = false, length = 100)
    private String title; // 제목

    @Column(nullable = false, length = 3000)
    private String contents; // 내용

    @Builder.Default
    @Column(nullable = false)
    private Integer views = 0; // 조회수 (기본값 0)

    @Column(nullable = false)
    private LocalDate regdate; // 작성 날짜

    @OneToMany(mappedBy = "reviewBoardId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewPhoto> reviewPhotos;

    
    @PrePersist
    public void prePersist() {
        // regdate가 null인 경우 현재 날짜를 설정
        if (this.regdate == null) {
            this.regdate = LocalDate.now();
        }

    }

    // 조회수 증가 메서드
    public void incrementViews() {
        this.views = (this.views == null ? 0 : this.views) + 1;
    }
}
