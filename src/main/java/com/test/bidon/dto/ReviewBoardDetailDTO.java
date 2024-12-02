package com.test.bidon.dto;

import java.time.LocalDate;
import java.util.List;

import com.test.bidon.entity.ReviewBoard;
import com.test.bidon.entity.ReviewPhoto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewBoardDetailDTO {
    private Integer id;
    private String title;
    private String contents;
    private Integer views;
    private LocalDate regdate;
    private String authorName;
    private List<String> photoPaths;
    private List<String> hashTags;
    private ReviewBoard review;
    private List<ReviewPhoto> reviewPhotos; // 리뷰 사진 추가
}