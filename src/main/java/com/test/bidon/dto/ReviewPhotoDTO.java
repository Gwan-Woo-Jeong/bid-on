package com.test.bidon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPhotoDTO {
    private Long id;
    private Long reviewBoardId;
    private String path;
}
