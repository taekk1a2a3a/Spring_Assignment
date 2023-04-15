package com.sparta.spring_lv1_assignment.Repository;

import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import com.sparta.spring_lv1_assignment.entity.Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardRepository {
    private static final Map<Long, Board> table = new HashMap<>();
    private static long ID;

    public String createBoard(Board board) {
        //ID 중복을 막기 위해서 ++ID
        board.setBoardId(++ID); //setter 로

        // 테이블에 생성한 Board 인스턴스를 저장
        table.put(ID, board);
        return "게시글 저장에 성공했습니다.";
    }

    public List<BoardResponseDto> getBoardList() {
        // 테이블에 저장되어있는 모든 게시글을 조회
        return table.values().stream().map(BoardResponseDto::new).collect(Collectors.toList());

    }

    public Board getBoard(Long boardId) {
        return table.get(boardId);
    }

    public void deleteBoard(Long boardId) {
        table.remove(boardId);
    }
}
