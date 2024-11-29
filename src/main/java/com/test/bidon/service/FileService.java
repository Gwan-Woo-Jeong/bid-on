package com.test.bidon.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
 private static final String UPLOAD_DIR = "C:/class/code/springBoot/bid-on/src/main/resources/static/user/images/review";

 public String saveFile(MultipartFile file) {
     if (file == null || file.isEmpty()) {
         return null;
     }

     try {
         // 고유한 파일 이름 생성
         String fileName = file.getOriginalFilename();
         Path filePath = Paths.get(UPLOAD_DIR, fileName);
         
         // 디렉토리 생성
         Files.createDirectories(filePath.getParent());

         // 파일 저장
         file.transferTo(filePath.toFile());

         return filePath.toString(); // 저장된 파일 경로 반환
     } catch (IOException e) {
         throw new RuntimeException("Failed to save file", e);
     }
 }
}
