package com.test.bidon.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

import com.test.bidon.entity.ReviewBoard;

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
}