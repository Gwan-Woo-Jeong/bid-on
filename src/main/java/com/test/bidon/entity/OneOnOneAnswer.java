package com.test.bidon.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "OneOnOneAnswer")
@Data
public class OneOnOneAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqOneOnOneAnswer")
    @SequenceGenerator(name = "seqOneOnOneAnswer", sequenceName = "seqOneOnOneAnswer", allocationSize = 1)
    private Integer id; // 1대1 답변 번호

    @ManyToOne
    @JoinColumn(name = "OneOnOneId", referencedColumnName = "id", nullable = false) // 실제 컬럼 이름을 명시
    private OneOnOne seqOneOnOne; // 질문 엔티티와 매핑

    @Column(nullable = false, length = 300)
    private String answer; // 답변 내용

    @Column(nullable = false)
    private LocalDate regdate; // 작성 시간

    @PrePersist
    public void prePersist() {
        this.regdate = LocalDate.now();
    }

	
}
