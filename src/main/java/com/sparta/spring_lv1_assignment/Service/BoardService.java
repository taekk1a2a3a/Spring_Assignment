package com.sparta.spring_lv1_assignment.Service;

import com.sparta.spring_lv1_assignment.Repository.BoardRepository;
import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import com.sparta.spring_lv1_assignment.entity.Board;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardService {

    private final BoardRepository boardRepository = new BoardRepository();

    public String createBoard(BoardRequestDto requestDto) {
        // 브라우저에서 받아온 데이터를 저장하기 위해서 Board 객체로 변환
        Board board = new Board(requestDto);

        return boardRepository.createBoard(board);
    }

    public List<BoardResponseDto> getBoardList() {
        // 테이블에 저장되어있는 모든 게시글을 조회
        return boardRepository.getBoardList();

    }

    public BoardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.getBoard(boardId);

        if (board != null) {
            return new BoardResponseDto(board);
        } else {
            return new BoardResponseDto();
        }
    }

    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto) {
        // 수정하기 위해 받아온 boardId 를 사용하여 해당 board 인스턴스가 존재하는지 확인하고 가져온다.
        Board board = boardRepository.getBoard(boardId);
        if (board != null) {
            board.update(requestDto);
            return new BoardResponseDto(board);
        } else {
            return new BoardResponseDto();
        }
    }

    public String deleteBoard(Long boardId) {
        Board board = boardRepository.getBoard(boardId);

        if (board != null) {
            boardRepository.deleteBoard(boardId);
            return "게시글 삭제 완료";
        } else {
            return "삭제할 게시글이 없습니다.";
        }
    }
}