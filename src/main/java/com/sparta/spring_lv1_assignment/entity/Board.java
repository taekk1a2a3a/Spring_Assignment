package com.sparta.spring_lv1_assignment.entity;

import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Board 라는 클래스에 들어갈 속성들을 정의해준다.
@Getter
@Entity // Board 라는 클래스를 DB의 Board 라는 테이블과 맵핑하는 클래스로써 사용한다. 즉, JPA 로써 사용한다
@NoArgsConstructor
public class Board {
    @Id // boardId 라는 필드를 만들고 걔를 private 키로 사용할 거에요 라고 알려주는 것
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 테이블마다 독립적으로 Id를 관리하겠다
    private Long boardId;
    private String boardTitle;
    private String boardContents;
    private String userId;
    private String userPw;
    public Board(BoardRequestDto requestDto) {
        this.boardTitle = requestDto.getBoardTitle();
        this.boardContents = requestDto.getBoardContents();
        this.userId = requestDto.getUserId();
        this.userPw = requestDto.getUserPw();
    }

    public void update(BoardRequestDto requestDto) {
        this.boardTitle = requestDto.getBoardTitle();
        this.boardContents = requestDto.getBoardContents();
    }

    // private 으로 접근제어자가 놓여있기 때문에 바로 직접 접근을 할 수 없기 때문에 Getter 와 Setter 로 우회해서 접근한다.
    // 매번 Getter 로 생성하기 비효율 적이기 때문에 Lombok 을 사용한다. -> @Getter
}
