package com.sparta.spring_lv1_assignment.entity;

import com.sparta.spring_lv1_assignment.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)입니다.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String userEmail;

    @Enumerated(value = EnumType.STRING) // STRING : 열거형 상수의 이름을 데이터베이스에 저장합니다. ORDINAL : 열거형 상수의 순서 값을 데이터베이스에 저장합니다.
    private UserRoleEnum role;

    public User(SignupRequestDto signupRequestDto) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.userEmail = signupRequestDto.getEmail();
        this.role = signupRequestDto.getRole();
    }
}
