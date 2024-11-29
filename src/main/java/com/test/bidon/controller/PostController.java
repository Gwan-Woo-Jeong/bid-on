package com.test.bidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.bidon.entity.ReviewPhoto;
import com.test.bidon.entity.Thumbnail;
import com.test.bidon.repository.ReviewPhotoRepository;
import com.test.bidon.repository.ThumbnailRepository;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private ThumbnailRepository thumbnailRepository;

    @Autowired
    private ReviewPhotoRepository reviewPhotoRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPost(
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam(value = "photos", required = false) MultipartFile[] photos) {
        try {
            // 1. 대표사진 저장
            Thumbnail thumbnailEntity = new Thumbnail();
            thumbnailEntity.setFileName(thumbnail.getOriginalFilename());
            thumbnailRepository.save(thumbnailEntity);

            // 2. 추가사진 저장
            if (photos != null && photos.length > 0) {
                for (MultipartFile photo : photos) {
                    ReviewPhoto reviewPhoto = new ReviewPhoto();
                    reviewPhoto.setFileName(photo.getOriginalFilename());
                    reviewPhoto.setThumbnail(thumbnailEntity); // Thumbnail과 연결
                    reviewPhotoRepository.save(reviewPhoto);
                }
            }

            return ResponseEntity.ok("대표사진 및 추가사진 업로드 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 실패: " + e.getMessage());
        }
    }
}