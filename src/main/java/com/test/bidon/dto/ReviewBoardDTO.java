package com.test.bidon.dto;

import com.test.bidon.entity.Thumbnail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewBoardDTO {

	private Integer id;
    private String title;
    private String contents;
    private Thumbnail thumbnail;
	
	
}
