package com.sparta.spring_lv1_assignment.dto;

import com.sparta.spring_lv1_assignment.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String userEmail;
    private boolean admin = false; // USER
    private String adminToken = ""; //
    private UserRoleEnum role = UserRoleEnum.USER;
}
