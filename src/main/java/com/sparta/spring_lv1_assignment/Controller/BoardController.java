package com.sparta.spring_lv1_assignment.Controller;

import com.sparta.spring_lv1_assignment.Service.BoardService;
import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String createBoard(@RequestBody BoardRequestDto.Create createDto) {
        return boardService.createBoard(createDto);
    }

    // 전체 조회 Read
    @GetMapping("/list")
    public List<BoardResponseDto> getBoardList() {
        return boardService.getBoardList();
    }
    // id로 조회 Read
    @GetMapping("/{boardId}")
    public BoardResponseDto getBoard(@PathVariable Long boardId)
    {
        return boardService.getBoard(boardId);
    }
    // 제목으로 조회 Read
    @GetMapping("/title/{boardTitle}")
    public BoardResponseDto getBoardByBoardTitle(@PathVariable String boardTitle) {
        return boardService.getBoardByBoardTitle(boardTitle);
    }

    // 수정 Update
    @PutMapping("/update")
    public String updateBoard(@RequestBody BoardRequestDto.Update updateDto) {
        return boardService.updateBoard(updateDto);
    }

    // 삭제 Delete
    @DeleteMapping("/delete")
    public String deleteBoard(@RequestBody BoardRequestDto.Delete deleteDto) {
        return boardService.deleteBoard(deleteDto);
    }
}
