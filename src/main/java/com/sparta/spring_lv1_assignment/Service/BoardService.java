package com.sparta.spring_lv1_assignment.Service;

import com.sparta.spring_lv1_assignment.Repository.BoardRepository;
import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import com.sparta.spring_lv1_assignment.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@Component // bean 으로 등록
@Transactional
@Service // @Service 에는 @Component 가 포함되어있지만 명확하게 얘는 Service 를 역할을 하는 bean 객체라는걸 협업 환경에서 표현하며 위해
public class BoardService {

    private final BoardRepository boardRepository;
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public String createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return "게시글 저장에 성공했습니다.";
    }

    public List<BoardResponseDto> getBoardList() {
        return boardRepository.findAll().stream().map(BoardResponseDto::new).collect(Collectors.toList());

    }

    public BoardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException(("선택한 게시글이 존재하지 않습니다."))
        );
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("선택한 게시글이 존재하지 않습니다."));

        board.update(requestDto);

        return new BoardResponseDto(board);

    }

    public String deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("선택한 게시글이 존재하지 않습니다."));

        boardRepository.delete(board); // -> id가 아니라 entity 객체를 넣어줘야한다 spring jpa 에서는

        return "게시글 삭제에 성공했습니다.";
    }
}
