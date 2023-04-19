package com.sparta.spring_lv1_assignment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String userEmail;
    private boolean admin = false;
    private String adminToken = "";
}
