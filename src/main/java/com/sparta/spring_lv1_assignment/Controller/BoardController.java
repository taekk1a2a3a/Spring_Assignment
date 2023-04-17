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


    private final BoardService boardService;  // = new BoardService(); new 객체가 매번 새롭게 만들어지지 않고
    // bean 에게 맡기기 위해  생성자 주입을 한다. Autowired
    @Autowired // 이렇게 생성자 하나만 있을 경우에는 생략 가능하다, 스프링 컨테이너에서 관리하는 bean 객체를 여기다 넣어주세요 하고 알려주는 것
    public BoardController(BoardService boardService){ // -< 이 경우도 component 로 어노테이션을 달아줘야한다.
        this.boardService = boardService;
    } // 이것도 @RequiredArgsConstructor 을 달면 생략 가능하다.

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
