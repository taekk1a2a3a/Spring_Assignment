package com.sparta.spring_lv1_assignment.Controller;

import com.sparta.spring_lv1_assignment.Service.BoardService;
import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;
    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @PostMapping("/create")
    public String createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/list")
    public List<BoardResponseDto> getBoardList() {
        return boardService.getBoardList();
    }

    @GetMapping("/{boardId}")
    public BoardResponseDto getBoard(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

    @PutMapping("/update/{boardId}")
    public BoardResponseDto updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(boardId, requestDto);
    }

    @DeleteMapping("/delete/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        return boardService.deleteBoard(boardId);
    }

    @GetMapping("/title/{boardTitle}")
        public BoardResponseDto getBoardByBoardTitle(@PathVariable String boardTitle) {
        return boardService.getBoardByBoardTitle(boardTitle);
    }
}
