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
    
    @OneToOne(mappedBy = "oneonone", fetch = FetchType.LAZY)	//종민아 oneononeanswer때문에 이것도 필요해서 만들었어. 보고나서 지워도돼~ -혜미-
    private OneOnOneAnswer oneOnOneAnswer;
    
}
