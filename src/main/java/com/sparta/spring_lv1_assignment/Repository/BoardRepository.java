package com.sparta.spring_lv1_assignment.Repository;

import com.sparta.spring_lv1_assignment.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

//Spring JPA -> interface 끼리의 상속은 extends
public interface BoardRepository extends JpaRepository<Board, Long> { // <레포지토리랑 연결할 테이블 클래스, id 타입>

}
