package com.test.bidon.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "OneOnOne")
@Data
public class OneOnOne {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqoneonone")
    @SequenceGenerator(name = "seqoneonone", sequenceName = "seqOneOnOne", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "USERINFOID", nullable = false) // 테이블의 실제 컬럼 이름
    private UserEntity userEntityInfo;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 300)
    private String contents;

    @Column(nullable = false)
    private LocalDate regdate;

    @PrePersist
    public void prePersist() {
        if (this.regdate == null) {
            this.regdate = LocalDate.now();
        }
    }
    
//    @OneToOne(mappedBy = "oneOnOne", fetch = FetchType.LAZY)	
//    private OneOnOneAnswer oneOnOneAnswer;
    
}
