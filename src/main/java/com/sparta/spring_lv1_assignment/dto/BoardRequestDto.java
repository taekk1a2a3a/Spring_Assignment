package com.sparta.spring_lv1_assignment.dto;

import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private String boardTitle;
    private String boardContents;
    private String userId;
    private String userPw;

}
