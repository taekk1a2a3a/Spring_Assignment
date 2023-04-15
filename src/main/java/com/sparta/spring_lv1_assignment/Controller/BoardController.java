package com.sparta.spring_lv1_assignment.Controller;

import com.sparta.spring_lv1_assignment.Service.BoardService;
import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
import com.sparta.spring_lv1_assignment.entity.Board;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

// 이 BoardController 라는 클래스가 웨스트 컨트롤러 라는 역할을 할거야 라고 정보를 전달한다.
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService = new BoardService();
    // 매 메서드마다 호출할 필요 없이 가장 상단에 만들어주고 바꿀일 없으니 final 달아준다.


    @PostMapping("/create")
    public String createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
        // 컨트롤러는 이게 끝 -> 브라우저에서 보내온 데이터를 잘 받고 서비스로 던져주면 된다.
        // RequestBody 어노테이션과 @PostMapping 어노테이션을 사용해서 api 매핑 받아서 받아온 데이터를 서비스 로 전달
        // String message = boardService.createBoard(requestDto);
        // return message; 를 생랄한 것이다. 반환타입이 String 이기 때문에
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
}
