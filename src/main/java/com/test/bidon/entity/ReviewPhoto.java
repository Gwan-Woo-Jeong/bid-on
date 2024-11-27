package com.test.bidon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ReviewPhoto")
@Data
public class ReviewPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_photo_seq_generator")
    @SequenceGenerator(name = "review_photo_seq_generator", sequenceName = "reviewPhotoId", allocationSize = 1)
	private Integer id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewBoardId", referencedColumnName = "id", nullable = false)
    private ReviewBoard reviewBoardId;

	
	@Column(nullable = false)
	private String path;
}
