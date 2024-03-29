package com.sparta.spring_lv1_assignment.dto;

import com.sparta.spring_lv1_assignment.entity.Board;
import com.sparta.spring_lv1_assignment.entity.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class BoardResponseDto extends Timestamped {
    private Long boardId;
    private String boardTitle;
    private String boardContents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.boardTitle = board.getBoardTitle();
        this.boardContents = board.getBoardContents();
        this.username = board.getUsername();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
