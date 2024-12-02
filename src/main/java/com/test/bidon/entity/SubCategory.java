package com.test.bidon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SubCategory")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subCategory_seq_generator")
    @SequenceGenerator(name = "subCategory_seq_generator", sequenceName = "seqSubCategory", allocationSize = 1)
    private Long id;

    @Column(name = "MainCategoryId")
    private Long mainCategoryId;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder
    public SubCategory(Long mainCategoryId, String name) {
        this.mainCategoryId = mainCategoryId;
        this.name = name;
    }
}
