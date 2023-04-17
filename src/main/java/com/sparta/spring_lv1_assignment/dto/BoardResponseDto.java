package com.sparta.spring_lv1_assignment.dto;

import com.sparta.spring_lv1_assignment.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class BoardResponseDto {
    private Long boardId;
    private String boardTitle;
    private String boardContents;
    private String userId;
    private String userPw;

    public BoardResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.boardTitle = board.getBoardTitle();
        this.boardContents = board.getBoardContents();
        this.userId = board.getUserId();
        this.userPw = board.getUserPw();
    }
// 이걸 써도 되고 lombok 에서 제공하는 @NoArgsConstructor 를 사용해도 된다. 매개변수가 없는 기본생성자
//    public BoardResponseDto() {
//    }
}
