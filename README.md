# Spring_Lv1_Assignment

# 1. 서비스 요구사항

- [x]  1. 아래의 요구사항을 기반으로 Use Case 그려보기
- [x]  2. 전체 게시글 목록 조회 API
    - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
    - 작성 날짜 기준 내림차순으로 정렬하기
- [x]  3. 게시글 작성 API
    - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고
    - 저장된 게시글을 Client 로 반환하기
- [x]  4. 선택한 게시글 조회 API
    - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
- [x]  5. 선택한 게시글 수정 API
    - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
- [x]  6. 선택한 게시글 삭제 API
    - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기

---

# 2. 기술 스택

- 언어 : Java 11
- IDE : IntelliJ IDEA
- 프레임워크 : Spring Boot 2.7.10, Spring Data JPA
- DB : 인 메모리 RDBMS(h2)
- 형상관리 : git https://github.com/taekk1a2a3a/Spring_Lv1_Assignment

---

# 3. package

### 3.1 Controller

- BoardController
    
    ```java
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
    
        @PutMapping("/update/{boardId}/{userPw}")
        public BoardResponseDto updateBoard(@PathVariable Long boardId, @PathVariable String userPw, @RequestBody BoardRequestDto requestDto) {
    
            return boardService.updateBoard(boardId, userPw, requestDto);
        }
    
        @DeleteMapping("/delete/{boardId}/{userPw}")
        public String deleteBoard(@PathVariable Long boardId, @PathVariable String userPw) {
            return boardService.deleteBoard(boardId, userPw);
        }
    
        @GetMapping("/title/{boardTitle}")
            public BoardResponseDto getBoardByBoardTitle(@PathVariable String boardTitle) {
            return boardService.getBoardByBoardTitle(boardTitle);
        }
    }
    ```
    

### 3.2 dto

- BoardRequestDto
    
    ```java
    package com.sparta.spring_lv1_assignment.dto;
    
    import lombok.Getter;
    
    @Getter
    public class BoardRequestDto {
        private String boardTitle;
        private String boardContents;
        private String userId;
        private String userPw;
    
    }
    ```
    
- BoardResponseDto
    
    ```java
    package com.sparta.spring_lv1_assignment.dto;
    
    import com.sparta.spring_lv1_assignment.entity.Board;
    import com.sparta.spring_lv1_assignment.entity.Timestamped;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    
    import java.time.LocalDateTime;
    
    @Getter
    @NoArgsConstructor
    //@AllArgsConstructor
    public class BoardResponseDto extends Timestamped {
        private Long boardId;
        private String boardTitle;
        private String boardContents;
        private String userId;
    //    private String userPw;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    
        public BoardResponseDto(Board board) {
            this.boardId = board.getBoardId();
            this.boardTitle = board.getBoardTitle();
            this.boardContents = board.getBoardContents();
            this.userId = board.getUserId();
    //        this.userPw = board.getUserPw();
            this.createdAt = board.getCreatedAt();
            this.modifiedAt = board.getModifiedAt();
    
        }
    }
    ```
    

### 3.3 entity

- Board
    
    ```java
    package com.sparta.spring_lv1_assignment.entity;
    
    import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    
    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;
    
    @Getter
    @Entity
    @NoArgsConstructor
    public class Board extends Timestamped{ //timestamped
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long boardId;
        private String boardTitle;
        private String boardContents;
        private String userId;
        private String userPw;
    
        public Board(BoardRequestDto requestDto) {
            this.boardTitle = requestDto.getBoardTitle();
            this.boardContents = requestDto.getBoardContents();
            this.userId = requestDto.getUserId();
            this.userPw = requestDto.getUserPw();
        }
    
        public void update(BoardRequestDto requestDto) {
            this.boardTitle = requestDto.getBoardTitle();
            this.boardContents = requestDto.getBoardContents();
            this.userId = requestDto.getUserId();
        }
    }
    ```
    
- TimeStamped
    
    ```java
    package com.sparta.spring_lv1_assignment.entity;
    
    import lombok.Getter;
    import org.springframework.data.annotation.CreatedDate;
    import org.springframework.data.annotation.LastModifiedDate;
    import org.springframework.data.jpa.domain.support.AuditingEntityListener;
    
    import javax.persistence.EntityListeners;
    import javax.persistence.MappedSuperclass;
    import java.time.LocalDateTime;
    
    @Getter
    @MappedSuperclass
    @EntityListeners(AuditingEntityListener.class)
    public class Timestamped {
    
        @CreatedDate
        private LocalDateTime createdAt;
    
        @LastModifiedDate
        private LocalDateTime modifiedAt;
    }
    ```
    

### 3.4 Repository

- BoardRepository
    
    ```java
    package com.sparta.spring_lv1_assignment.Repository;
    
    import com.sparta.spring_lv1_assignment.entity.Board;
    import org.springframework.data.jpa.repository.JpaRepository;
    
    import java.util.Optional;
    
    public interface BoardRepository extends JpaRepository<Board, Long> {
    
        Optional<Board> findByBoardTitle(String boardTitle);
        Optional<Board> findByUserPw(String userPw);
    
    }
    ```
    

### 3.5 Service

- BoardService
    
    ```java
    package com.sparta.spring_lv1_assignment.Service;
    
    import com.sparta.spring_lv1_assignment.Repository.BoardRepository;
    import com.sparta.spring_lv1_assignment.dto.BoardRequestDto;
    import com.sparta.spring_lv1_assignment.dto.BoardResponseDto;
    import com.sparta.spring_lv1_assignment.entity.Board;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Sort;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    
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
        public String createBoard(BoardRequestDto requestDto) {
            Board board = new Board(requestDto);
            boardRepository.save(board);
            return "게시글 저장에 성공했습니다.";
        }
    
        //  전체 게시글 목록 조회
        public List<BoardResponseDto> getBoardList() {
    		//return boardRepository.findAll().stream().map(BoardResponseDto::new).collect(Collectors.toList());
            // 내림차순 정렬하기
            return boardRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt")).stream().map(BoardResponseDto::new).collect(Collectors.toList());
    
        }
    
        // 선택한 게시글 조회
        public BoardResponseDto getBoard(Long boardId) {
            Board board = checkBoard(boardId);
            return new BoardResponseDto(board);
        }
    
        // 게시글 수정
        @Transactional
        public BoardResponseDto updateBoard(Long boardId, String userPw, BoardRequestDto requestDto) {
            Board board = checkBoard(boardId);
            checkPw(userPw);
            board.update(requestDto);
    
            return new BoardResponseDto(board);
    
        }
    
        // 게시글 삭제
        public String deleteBoard(Long boardId, String userPw) {
            Board board = checkBoard(boardId);
            checkPw(userPw);
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
    
        // 공통 method : pw 확인 method
        private void checkPw(String userPw) {
            boardRepository.findByUserPw(userPw).orElseThrow(
                    () -> new NullPointerException(("비밀번호가 일치하지 않습니다."))
            );
        }
    }
    ```
    

### 3.6 그 외

- Application.java
    
    ```java
    package com.sparta.spring_lv1_assignment;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
    
    @EnableJpaAuditing //timestamped
    @SpringBootApplication
    public class SpringLv1AssignmentApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(SpringLv1AssignmentApplication.class, args);
        }
    
    }
    ```
    
- application.properties
    
    ```java
    #localhost:8080/h2-console
    spring.h2.console.enabled=true
    spring.datasource.url=jdbc:h2:mem:db:MODE=MYSQL;
    spring.datasource.username=sa
    spring.datasource.password=
    
    spring.thymeleaf.cache=false
    
    # 터미널에서 sql 쿼리 작성해줌
    spring.jpa.properties.hibernate.show_sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.use_sql_comments=true
    ```
    

---

# 4. API 명세서

| 기능 | Method | URL | Request | Response |
| --- | --- | --- | --- | --- |
| 메인 | GET | /board |  |  |
| 생성(C) | POST | /board/create | {
"boardTitle" : "title",
"boardContents" : "contents",
"userId" : "userId",
"userPw" : "userPw"
} | “게시글 저장에 성공했습니다.” |
| 조회(R) | GET | /board/list |  | {
"createdAt": "2023-04-17T17:12:45.476824",
"modifiedAt": "2023-04-17T17:12:45.476824",
"boardId": 1,
"boardTitle": "boardTitle",
"boardContents": "boardContents",
"userId": "userId"
} |
| 단일조회(R) | GET | /board/{boardId} |  | {
"createdAt": "2023-04-17T17:19:25.568108",
"modifiedAt": "2023-04-17T17:19:25.568108",
"boardId": 1,
"boardTitle": "boardTitle",
"boardContents": "boardContents",
"userId": "userId"
} |
| 수정(U) | PUT | /board/{boardTitle},{userPw} | {
"boardTitle" : "update",
"boardContents" : "update",
"userId" : "update"
} | {
"createdAt": "2023-04-17T17:19:25.568108",
"modifiedAt": "2023-04-17T17:19:25.568108",
"boardId": 1,
"boardTitle": "update",
"boardContents": "update",
"userId": "update"
} |
| 삭제(D) | DELETE | /board/{boardTitle},{userPw} |  | “게시글 삭제에 성공했습니다.” |

---

# 5. Why?

1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
    1. 수정(Update)
        1. @PathVariable을 사용하여 URL경로에서 {boardId}와 {userPw}를 가져온다 - Parameter
        2. @RequestBody를 사용하여 요청 body에서 BoardRequestDto 객체를 가져온다 - Body
    2. 삭제(Delete)
        1. @PathVariable를 사용하여 URL 경로에서 {boardId}와 {userPw}를 가져온다. - Parameter
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
    1. 생성(Create) : POST 메소드와 RequestBody를 사용한다.
    2. 조회(Read) : GET 메소드와 Query, 파라미터를 사용한다.
    3. 수정(Update) : PUT 또는 PATCH 메소드와 RequestBody를 사용한다.
    4. 삭제(Delete) : DELETE 메소드와 URL 경로 파라미터를 사용합니다.
3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
    1. 자원을 URI로 표현하고, HTTP 메소드를 사용하여 자원에 대한 CRUD작업을 수행하며, 데이터 표현 방식으로 JSON 사용했다.
    2. 전체적인 에러 예외 처리가 부족한 것 같다. 파라미터 요청방식을 많이 채택했고 DB의 암호화가 적용되지않아 보안적인 측면에서 노출되기 쉽다.
4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
    1. 간단하고 일관된 API 디자인을 제공하고있으며, Controller, Dto, Entity, Repo, Service등의 패키지를 나누어 클라이언트와 서버 간의 분리를 극대화하여 시스템을 더욱 유연하고 확장 가능하게 만들었다.
5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
    1. Request Header와 Response Elements의 타입, 필수여부, 설명이 부족한 것 같다.
    2. 시각적 UI를 활용하여 테이블을 계층화 시키고 코드블럭을 활용해야겠다.
