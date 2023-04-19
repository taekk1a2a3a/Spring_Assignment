package com.sparta.spring_lv1_assignment.Controller;

import com.sparta.spring_lv1_assignment.Service.UserService;
import com.sparta.spring_lv1_assignment.dto.LoginRequestDto;
import com.sparta.spring_lv1_assignment.dto.SignupRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    @Valid
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @ResponseBody //ajax 에서 body 쪽에 값이 넘어오기 때문에 @RequestBody 를 써줘야 한다.
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // HttpServletResponse 는 HttpRequest 에서 Header 가 넘어와서 받아오는 것 처럼 Client 쪽으로 반환을 할 때에는 response 객체를 반환한다.
        // 반환 할 response Header 에 만들어준 토큰을 넣어주기 위해 받아온다.
        return userService.login(loginRequestDto, response);
    }
}
