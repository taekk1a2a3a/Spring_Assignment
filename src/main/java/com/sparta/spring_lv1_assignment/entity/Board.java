package com.sparta.spring_lv1_assignment.entity;

import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import lombok.Getter;

// Board 라는 클래스에 들어갈 속성들을 정의해준다.
@Getter
public class Board {
    // private 으로 캡슐 -> 데이터를 안정성있게 관리하기 위해서
    private Long boardId;
    private String boardTitle;
    private String boardContents;
    private String userId;
    private String userPw;

    public void setBoardId(long boardId) { //setter
        this.boardId = boardId;
    }

    public Board(BoardRequestDto requestDto) {
        this.boardTitle = requestDto.getBoardTitle();
        this.boardContents = requestDto.getBoardContents();
        this.userId = requestDto.getUserId();
        this.userPw = requestDto.getUserPw();
    }

    public void update(BoardRequestDto requestDto) {
        this.boardTitle = getBoardTitle();
        this.boardContents = getBoardContents();
    }

    // private 으로 접근제어자가 놓여있기 때문에 바로 직접 접근을 할 수 없기 때문에 Getter 와 Setter 로 우회해서 접근한다.
    // 매번 Getter 로 생성하기 비효율 적이기 때문에 Lombok 을 사용한다. -> @Getter
}
