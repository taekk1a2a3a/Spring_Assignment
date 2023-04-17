package com.sparta.spring_lv1_assignment.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String boardTitle;
    private String boardContents;
    private String userId;
    private String userPw;

}
