package com.sparta.spring_lv1_assignment.dto;

import com.sparta.spring_lv1_assignment.entity.Board;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    // 받아야될 정보만 속성으로 선언을 해준다. boardId는 받지 않았다.
    private String boardTitle;
    private String boardContents;
    private String userId;
    private String userPw;


}
