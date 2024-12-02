package com.test.bidon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "REVIEWPHOTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewPhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewBoardId", nullable = false)
    private ReviewBoard reviewBoard;

    @Column(name = "path" ,length = 300, nullable = false)
    private String reviewPhotoPath;
    
	public String getReviewPhotoPath() {
	  return reviewPhotoPath;
	}
	
	public void setReviewPhotoPath(String reviewPhotoPath) {
	  this.reviewPhotoPath = reviewPhotoPath;
	}
}
