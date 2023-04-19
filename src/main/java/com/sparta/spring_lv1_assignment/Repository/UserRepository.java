package com.sparta.spring_lv1_assignment.Repository;

import com.sparta.spring_lv1_assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
