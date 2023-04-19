package com.sparta.spring_lv1_assignment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String userEmail;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String userEmail, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.userEmail = userEmail;
        this.role = role;
    }
}
