package com.sparta.spring_lv1_assignment.Service;

import com.sparta.spring_lv1_assignment.Repository.UserRepository;
import com.sparta.spring_lv1_assignment.dto.LoginRequestDto;
import com.sparta.spring_lv1_assignment.dto.SignupRequestDto;
import com.sparta.spring_lv1_assignment.entity.User;
import com.sparta.spring_lv1_assignment.entity.UserRoleEnum;
import com.sparta.spring_lv1_assignment.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public String signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String userEmail = signupRequestDto.getUserEmail();

        // 회원가입 ID 확인
        if (username.length() < 4 || username.length() > 10) {
            throw new IllegalArgumentException("아이디는 4자이상 10자 이하로 작성해주세요");
        }
        if (username.matches(".*[^a-z0-9].*")){
            throw new IllegalArgumentException("아이디는 알파벳 소문자와 숫자로만 구성될 수 있습니다.");
        }
        // 회원가입 PW 확인
        if (password.length() < 8 || password.length() > 15) {
            throw new IllegalArgumentException("비밀번호는 8자이상 15자 이하로 작성해주세요");
        }
        if (!password.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+")) {
            throw new IllegalArgumentException("비밀번호는 알파벳과 숫자로만 입력가능합니다.");
        }

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, userEmail, role);
        userRepository.save(user);
        return "회원가입 완료";
    }

    // 로그인
    @Transactional(readOnly = true)
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return "로그인 성공";
    }
}
