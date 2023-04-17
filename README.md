# Spring Lv.1 Assignment

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
    
    
    

### 3.2 dto

- BoardRequestDto
    
    
    
- BoardResponseDto
    
    
    

### 3.3 entity

- Board
    
    
    
- TimeStamped
    
    
    

### 3.4 Repository

- BoardRepository
    
    
    

### 3.5 Service

- BoardService
    
    
    

### 3.6 그 외

- Application.java
    
    
    
- application.properties
    
    
    

---

# 4. API 명세서

| 기능 | Method | URL | Request | Response |
| --- | --- | --- | --- | --- |
| 메인 | GET | /board |  |  |
| 생성(C) | POST | /board/create | {<br> "boardTitle" : "title",<br> "boardContents" : "contents",<br> "userId" : "userId",<br> "userPw" : "userPw"<br> } | “게시글 저장에 성공했습니다.” |
| 조회(R) | GET | /board/list |  | {<br> "createdAt": "2023-04-17T17:12:45.476824",<br> "modifiedAt": "2023-04-17T17:12:45.476824",<br> "boardId": 1,<br> "boardTitle": "boardTitle",<br> "boardContents": "boardContents",<br> "userId": "userId"<br> } |
| 단일조회(R) | GET | /board/{boardId} |  | {<br> "createdAt": "2023-04-17T17:19:25.568108",<br> "modifiedAt": "2023-04-17T17:19:25.568108",<br> "boardId": 1,<br> "boardTitle": "boardTitle",<br> "boardContents": "boardContents",<br> "userId": "userId"<br> } |
| 수정(U) | PUT | /board/{boardTitle},{userPw} | {<br> "boardTitle" : "update",<br> "boardContents" : "update",<br> "userId" : "update"<br> } | {<br> "createdAt": "2023-04-17T17:19:25.568108",<br> "modifiedAt": "2023-04-17T17:19:25.568108",<br> "boardId": 1,<br> "boardTitle": "update",<br> "boardContents": "update",<br> "userId": "update"<br> } |
| 삭제(D) | DELETE | /board/{boardTitle},{userPw} |  | “게시글 삭제에 성공했습니다.” |

---

# 5. Why?

### 1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)

- 수정(Update)
    - @PathVariable을 사용하여 URL경로에서 {boardId}와 {userPw}를 가져온다 - Parameter
    - @RequestBody를 사용하여 요청 body에서 BoardRequestDto 객체를 가져온다 - Body
- 삭제(Delete)
    - @PathVariable를 사용하여 URL 경로에서 {boardId}와 {userPw}를 가져온다. - Parameter
<br>

### 2. 어떤 상황에 어떤 방식의 request를 써야하나요?

- 생성(Create) : POST 메소드와 RequestBody를 사용한다.
- 조회(Read) : GET 메소드와 Query, 파라미터를 사용한다.
- 수정(Update) : PUT 또는 PATCH 메소드와 RequestBody를 사용한다.
- 삭제(Delete) : DELETE 메소드와 URL 경로 파라미터를 사용합니다.

<br>

### 3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?

- 자원을 URI로 표현하고, HTTP 메소드를 사용하여 자원에 대한 CRUD작업을 수행하며, 데이터 표현 방식으로 JSON 사용했다.
- 전체적인 에러 예외 처리가 부족한 것 같다. 파라미터 요청방식을 많이 채택했고 DB의 암호화가 적용되지않아 보안적인 측면에서 노출되기 쉽다.

<br>

### 4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)

- 간단하고 일관된 API 디자인을 제공하고있으며, Controller, Dto, Entity, Repo, Service등의 패키지를 나누어 클라이언트와 서버 간의 분리를 극대화하여 시스템을 더욱 유연하고 확장 가능하게 만들었다.
<br>

### 5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!

- Request Header와 Response Elements의 타입, 필수여부, 설명이 부족한 것 같다.
- 시각적 UI를 활용하여 테이블을 계층화 시키고 코드블럭을 활용해야겠다.
