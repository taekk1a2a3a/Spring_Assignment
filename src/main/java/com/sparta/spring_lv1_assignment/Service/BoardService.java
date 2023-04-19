package com.sparta.spring_lv1_assignment.Service;

import com.sparta.spring_lv1_assignment.Repository.BoardRepository;
import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import com.sparta.spring_lv1_assignment.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //  게시글 작성
    public String createBoard(BoardRequestDto.Create createDto) {
        Board board = new Board(createDto);
        boardRepository.save(board);
        return "게시글 저장에 성공했습니다.";
    }

    //  전체 게시글 목록 조회
    public List<BoardResponseDto> getBoardList() {
            return boardRepository.findAll()
                    .stream()
                    .sorted(Comparator.comparing(Board::getCreatedAt).reversed())
                    .map(BoardResponseDto::new)
                    .collect(Collectors.toList());
    }

    // 선택한 게시글 조회
    public BoardResponseDto getBoard(Long boardId) {
        Board board = checkBoard(boardId);
        return new BoardResponseDto(board);
    }

    // 게시글 수정
    @Transactional
    public String updateBoard(BoardRequestDto.Update updateDto) {
        Board board = checkBoard(updateDto.getBoardId());
        checkPw(updateDto.getUserPw());
        board.update(updateDto);
        return "게시글이 수정되었습니다.";
    }


    // 게시글 삭제
    public String deleteBoard(BoardRequestDto.Delete deleteDto) {
        Board board = checkBoard(deleteDto.getBoardId());
        checkPw(deleteDto.getUserPw());
        boardRepository.delete(board);

        return "게시글 삭제에 성공했습니다.";
    }

    // 게시글 제목으로 조회
    public BoardResponseDto getBoardByBoardTitle(String boardTitle) {
        Board board = boardRepository.findByBoardTitle(boardTitle).orElseThrow(
                () -> new NullPointerException("해당하는 제목의 게시글이 없습니다..")
        );

        return new BoardResponseDto(board);

    }

    // 공통 method : 게시글 db 에서 찾고 없으면 exception throw
    private Board checkBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException(("선택한 게시글이 존재하지 않습니다."))
        );
    }

    // 공통 method: pw 확인 method
    private void checkPw(String userPw) {
        boardRepository.findByUserPw(userPw).orElseThrow(
                () -> new NullPointerException(("비밀번호가 일치하지 않습니다."))
        );
    }
}
