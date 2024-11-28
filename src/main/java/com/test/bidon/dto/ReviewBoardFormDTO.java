package com.test.bidon.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ReviewBoardFormDTO {
	 private String title;               // 제목
	    private String contents;            // 내용
	    private String email;               // 사용자 이메일
	    private MultipartFile thumbnail;    // 대표 이미지
	    private List<MultipartFile> photos; // 추가 이미지 리스트

    // Getters and Setters

	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public MultipartFile getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(MultipartFile thumbnail) {
		this.thumbnail = thumbnail;
	}
	public List<MultipartFile> getPhotos() {
		return photos;
	}
	public void setPhotos(List<MultipartFile> photos) {
		this.photos = photos;
	}
	
    
    
}
