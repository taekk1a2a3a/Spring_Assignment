package com.sparta.spring_lv1_assignment.entity;

import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity // JPA 를 사용하려면 데이터베이스와 연동되는 클래스를 엔티티 클래스로 지정해야한다. 이를 통해 db 에 쉽게 저장하고 조회할 수 있다.
@NoArgsConstructor
public class Board extends Timestamped{ //timestamped

    @Id // @Id 를 사용하여 해당 필드가 Primary Key 임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT 와 같은 역할 자동으로 값을 생성해주는 방식
    private Long boardId;
    private String boardTitle;
    private String boardContents;
    private String userId;
    private String userPw;

    // 생성자의 이름은 클래스 이름과 동일해야한다. 생성자는 클래스의 인스턴스를 초기화하는 특별한 종류의 메소드 이기 때문이다.
    public Board(BoardRequestDto requestDto) {
        this.boardTitle = requestDto.getBoardTitle();
        this.boardContents = requestDto.getBoardContents();
        this.userId = requestDto.getUserId();
        this.userPw = requestDto.getUserPw();
    }

    // Board 객체의 상태를 변경하는데 사용되며 이 메서드 자체가 새로운 객체를 반환하지 않는다.
    public void update(BoardRequestDto requestDto) {
        this.boardTitle = requestDto.getBoardTitle();
        this.boardContents = requestDto.getBoardContents();
        this.userId = requestDto.getUserId();
    }
}
