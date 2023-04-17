package com.sparta.spring_lv1_assignment.Repository;

import com.sparta.spring_lv1_assignment.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Spring JPA -> interface 끼리의 상속은 extends
public interface BoardRepository extends JpaRepository<Board, Long> { // <레포지토리랑 연결할 테이블 클래스, id 타입>
    // spring jpa 에서는 id로 조회하는 것 밖에 제공되지 않기 때문에
    // 특정 게시글의 제목이나 내용으로 검색하기 위해서 query method 를 사용

    Optional<Board> findByBoardTitle(String boardTitle);
}
