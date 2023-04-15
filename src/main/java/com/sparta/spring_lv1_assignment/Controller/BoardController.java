package com.sparta.spring_lv1_assignment.Controller;

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
    // 이러한 꺾새가 Generics 이다. 컬렉션 프레임 워크, 맵이나 리스트 같은 애들은 기본적으로 제네릭스로 만들어져 있고 스프링 내부 코드도 거의 제네릭스 형태로 되어있다.
    private static final Map<Long, Board> table = new HashMap<>(); // 데이터베이스 테이블로 일단 생각
    private static long ID; // 이 ID는 Map 의 Key로 넣어줘야 하기 때문에 만들어준다. Long 말고 원시타입인 long으로

    @PostMapping("/create")
    public String createBoard(@RequestBody BoardRequestDto requestDto){
        // public String createBoard(@RequestBody Board board) , 즉 클래스로 받을 수 있지만 중요한 정보이기 때문에
        // BoardRequestDto 라는 요청을 받을 때 사용하는 클래스를 따로 만들어서 정보를 받아오자.
        // 브라우저에서 받아온 데이터를 저장하기 위해서 Board 객체로 변환
        // -> Board 라는 객체를 사용해야 데이터베이스에 저장을 할 수 있기 때문에
        Board board = new Board(requestDto);

        //ID 중복을 막기 위해서 ++ID
        board.setBoardId(++ID); //setter 로

        // 테이블에 생성한 Board 인스턴스를 저장
        table.put(ID, board);

        return "게시글 저장에 성공했습니다.";

    }

    @GetMapping("/list")
    public List<BoardResponseDto> getBoardList() {
        // 테이블에 저장되어있는 모든 게시글을 조회
        return table.values().stream().map(BoardResponseDto::new).collect(Collectors.toList());

    }

    @GetMapping("/{boardId}")
    public BoardResponseDto getBoard(@PathVariable Long boardId){
        // 조회하기 위해 받아온 boardId 를 사용해서 해당 id의 board 인스턴스가 테이블에 존재하는지 확인하고 가져온다.
        Board board = table.get(boardId);
        if (board != null){
            return new BoardResponseDto(board);
        } else {
            return new BoardResponseDto();
            // 일반적으로 기본 생성자를 자바 컴파일러가 만들어주지만 오버로딩된 생성자가 하나라도 존재하는 경우에 자동으로 기본생성자를 만들어 주지 않는다.
            // 기본생성자라는 것은 select none 을 의미
        }
    }

    @PutMapping("/update/{boardId}")
    public BoardResponseDto updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto){
        // 수정하기 위해 받아온 boardId 를 사용하여 해당 board 인스턴스가 존재하는지 확인하고 가져온다.
        Board board = table.get(boardId);
        if (board != null){
            board.update(requestDto);
            return new BoardResponseDto(board);
        } else {
            return new BoardResponseDto();
        }
    }

    @DeleteMapping("/delete/{boardId}")
    public String deleteBoard(@PathVariable Long boardId){
        Board board = table.get(boardId);

        if (board != null){
            table.remove(boardId);
            return "게시글 삭제 완료";
        } else {
            return "삭제할 게시글이 없습니다.";
        }
    }
}
