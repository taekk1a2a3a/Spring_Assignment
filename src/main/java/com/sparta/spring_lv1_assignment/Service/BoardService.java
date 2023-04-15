package com.sparta.spring_lv1_assignment.Service;

import com.sparta.spring_lv1_assignment.Repository.BoardRepository;
import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import com.sparta.spring_lv1_assignment.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component // bean 으로 등록
@Service // @Service 에는 @Component 가 포함되어있지만 명확하게 얘는 Service 를 역할을 하는 bean 객체라는걸 협업 환경에서 표현하며 위해
public class BoardService {

    private final BoardRepository boardRepository;
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

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
