package com.sparta.spring_lv1_assignment.Repository;

import com.sparta.spring_lv1_assignment.entity.Board;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {
    @PersistenceContext
    EntityManager em; // JPA 에서 Entity 를 관리해주는 것

    @Transactional // 등록
    public void save(Board board) {
        em.persist(board);
    }

    @Transactional // 단일 조회
    public Optional<Board> findById(Long id) {
        Board board = em.find(Board.class, id);
        return Optional.ofNullable(board);
    }

    @Transactional //모두 조회
    public List<Board> findAll() {
        return em.createQuery("select c from Board c", Board.class).getResultList();
    }

//    @Transactional //수정
//    public void update(Board board) {
//        board.update(requestDto);
//    }

    @Transactional //삭제
    public void delete(Long id) {
        Board board = em.find(Board.class, id);
        em.remove(board);
    }
}
