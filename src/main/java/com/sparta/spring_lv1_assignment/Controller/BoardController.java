package com.sparta.spring_lv1_assignment.Controller;

import com.sparta.spring_lv1_assignment.Service.BoardService;
import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import com.sparta.spring_lv1_assignment.dto.StatusMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;
    @Autowired
    @Valid
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    // 생성 Create
    @PostMapping("/create")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto.Create createDto, HttpServletRequest request) {
        return boardService.createBoard(createDto, request);
    }

    // 전체 조회 Read
    @GetMapping("/list")
    public List<BoardResponseDto> getBoardList() {
        return boardService.getBoardList();
    }

    // id로 조회 Read
    @GetMapping("/list/{boardId}")
    public BoardResponseDto getBoard(@PathVariable Long boardId)
    {
        return boardService.getBoard(boardId);
    }

    // 제목으로 조회 Read
    @GetMapping("/search") // 제목이 정확히 일치해야 나온다. -> 추후에 검색 기능으로 업그레이드 필요 (키워드로 검색하기)
    public BoardResponseDto getBoardByBoardTitle(@RequestParam("q") String boardTitle) {
        return boardService.getBoardByBoardTitle(boardTitle);
    }

    // 수정 Update
    @PutMapping("/update")
    public BoardResponseDto updateBoard(@RequestBody BoardRequestDto.Update updateDto, HttpServletRequest request) {
        return boardService.updateBoard(updateDto, request);
    }

    // 삭제 Delete
    @DeleteMapping("/delete")
    public ResponseEntity<StatusMessageDto> deleteBoard(@RequestBody BoardRequestDto.Delete deleteDto, HttpServletRequest request) {
        return boardService.deleteBoard(deleteDto, request);
    }
}
