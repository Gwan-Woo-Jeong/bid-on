package com.test.bidon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload.review.dir}")
    private String reviewDir;

    @Value("${file.upload.profile.dir}")
    private String profileDir;

    @Value("${file.upload.banner.dir}")
    private String bannerDir;

    /**
     * 파일 저장 메서드: 파일 유형에 따라 저장 디렉토리를 선택하여 저장.
     * 
     * @param file     저장할 파일
     * @param fileType 파일 유형 (review, profile, banner 등)
     * @return 저장된 파일의 상대 경로 (URL 형식)
     */
    public String saveFile(MultipartFile file, String fileType) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // 파일 저장 경로 선택
            String uploadDir = switch (fileType.toLowerCase()) {
                case "review" -> reviewDir;
                case "profile" -> profileDir;
                case "banner" -> bannerDir;
                default -> throw new IllegalArgumentException("Invalid file type: " + fileType);
            };

            // 고유한 파일 이름 생성
            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, uniqueFileName);

            // 디렉토리 생성
            Files.createDirectories(filePath.getParent());

            // 파일 저장
            file.transferTo(filePath.toFile());

            // 상대 경로 반환
            return "/user/images/" + fileType + "/" + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 리뷰 파일 저장을 위한 별도의 간소화된 메서드
     * 
     * @param file 저장할 파일
     * @return 저장된 파일의 상대 경로
     */
    public String saveReviewFile(MultipartFile file) {
        return saveFile(file, "review");
    }
    
    public void deleteFile(String filePath) {
    	if (filePath == null || filePath.isEmpty())
    		return;
    	
    	
    	try {
			
		} catch (Exception e) {
			throw new RuntimeException("파일 삭제 중 오류 발생" + filePath, e);
		}
    }
    
}
