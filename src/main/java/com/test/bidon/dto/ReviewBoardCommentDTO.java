package com.test.bidon.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewBoardCommentDTO {
    private String name;
    private String email;
    private String message;
}