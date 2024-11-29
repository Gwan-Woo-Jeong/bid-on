package com.test.bidon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Thumbnail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Thumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "review_board_id", nullable = false) // ReviewBoard ID와 매핑
    private Integer reviewBoardId;

    @Column(name = "file_path", nullable = false)
    private String filePath;

	public void setFileName(String originalFilename) {

		
	}
	
	
	private Integer reviewPhotoId; // 필드 추가

	public Integer getReviewPhotoId() {
        return reviewPhotoId;
    }

    public void setReviewPhotoId(Integer reviewPhotoId) {
        this.reviewPhotoId = reviewPhotoId;
    }
	
	
}

