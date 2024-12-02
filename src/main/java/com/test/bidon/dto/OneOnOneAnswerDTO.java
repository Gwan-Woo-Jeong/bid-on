package com.test.bidon.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneOnOneAnswerDTO {

    private Integer id; // 답변 ID
    private Integer oneOnOneId; // 질문 ID
    private String answer; // 답변 내용
    private LocalDateTime regDate; // 답변 등록일
}