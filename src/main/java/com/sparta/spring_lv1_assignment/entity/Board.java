package com.sparta.spring_lv1_assignment.entity;

import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{ //timestamped
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        this.userId = requestDto.getUserId();
    }
}
