package com.test.bidon.dto;

import java.util.Date;

public class OneOnOneDTO {

    private Long id; // NUMBER(38)
    private Long userInfoId; // NUMBER(38)
    private String title; // VARCHAR2(100)
    private String contents; // VARCHAR2(300)
    private String answer; // VARCHAR2(300)
    private Date regDate; // DATE

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "OneOnOneDTO{" +
               "id=" + id +
               ", userInfoId=" + userInfoId +
               ", title='" + title + '\'' +
               ", contents='" + contents + '\'' +
               ", answer='" + answer + '\'' +
               ", regDate=" + regDate +
               '}';
    }
}
