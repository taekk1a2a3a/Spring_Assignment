package com.sparta.spring_lv1_assignment.Service;

import com.sparta.spring_lv1_assignment.Repository.BoardRepository;
import com.sparta.spring_lv1_assignment.Repository.UserRepository;
import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import com.sparta.spring_lv1_assignment.dto.StatusMessageDto;
import com.sparta.spring_lv1_assignment.entity.Board;
import com.sparta.spring_lv1_assignment.entity.StatusEnum;
import com.sparta.spring_lv1_assignment.entity.User;
import com.sparta.spring_lv1_assignment.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public BoardService(UserRepository userRepository, BoardRepository boardRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.jwtUtil = jwtUtil;
    }

    //  게시글 작성
    public BoardResponseDto createBoard(BoardRequestDto.Create createDto, HttpServletRequest request) {
        User user = checktoken(request);
//        boardRepository.save(new Board(createDto, user));
        return new BoardResponseDto(boardRepository.save(new Board(createDto, user)));
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
    public BoardResponseDto updateBoard(BoardRequestDto.Update updateDto, HttpServletRequest request) {
        User user = checktoken(request);
        Board board = boardRepository.findByBoardIdAndUsername(updateDto.getBoardId(), user.getUsername()).orElseThrow(
                () -> new NullPointerException("본인의 글만 수정할 수 있습니다.")
        );
        board.update(updateDto, user);
        return new BoardResponseDto(board);
    }

    // 게시글 삭제
    public ResponseEntity<StatusMessageDto> deleteBoard(BoardRequestDto.Delete deleteDto, HttpServletRequest request) {
        User user = checktoken(request);
        Board board = boardRepository.findByBoardIdAndUsername(deleteDto.getBoardId(), user.getUsername()).orElseThrow(
                () -> new NullPointerException("본인의 글만 삭제할 수 있습니다.")
        );
        boardRepository.delete(board);
        StatusMessageDto statusMessageDto = StatusMessageDto.setSuccess(StatusEnum.OK.getStatusCode(), "게시글 삭제 완료", null);
        return new ResponseEntity(statusMessageDto, HttpStatus.OK);
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

    // 공통 method : 토큰 확인
    private User checktoken(HttpServletRequest request) {
        // 토근 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검증
        if (jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("로그인 후 이용해 주세요");
        }

        return userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다.")
        );
    }
}
