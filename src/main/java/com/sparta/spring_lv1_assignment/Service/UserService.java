package com.sparta.spring_lv1_assignment.Service;

import com.sparta.spring_lv1_assignment.Repository.UserRepository;
import com.sparta.spring_lv1_assignment.dto.LoginRequestDto;
import com.sparta.spring_lv1_assignment.dto.SignupRequestDto;
import com.sparta.spring_lv1_assignment.dto.StatusMessageDto;
import com.sparta.spring_lv1_assignment.entity.StatusEnum;
import com.sparta.spring_lv1_assignment.entity.User;
import com.sparta.spring_lv1_assignment.entity.UserRoleEnum;
import com.sparta.spring_lv1_assignment.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<StatusMessageDto> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 사용자 ROLE 확인
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            signupRequestDto.setRole(UserRoleEnum.ADMIN);
        }

        User user = new User(signupRequestDto);
        userRepository.save(user);
        // 상태 코드
        StatusMessageDto statusMessageDto = StatusMessageDto.setSuccess(StatusEnum.OK.getStatusCode(), "사용자 로그인 완료", null);
        if (user.getRole() == UserRoleEnum.ADMIN){
            statusMessageDto = StatusMessageDto.setSuccess(StatusEnum.OK.getStatusCode(), "관리자 로그인 완료", null);
        }
        return new ResponseEntity(statusMessageDto, HttpStatus.OK);
    }

    // 로그인
    @Transactional(readOnly = true)
    public ResponseEntity<StatusMessageDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 사용자 확인
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(loginRequestDto.getPassword())){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 토큰 부여
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        // 상태 코드
        StatusMessageDto statusMessageDto = StatusMessageDto.setSuccess(StatusEnum.OK.getStatusCode(), "사용자 로그인 완료", null);
        if (user.getRole() == UserRoleEnum.ADMIN){
            statusMessageDto = StatusMessageDto.setSuccess(StatusEnum.OK.getStatusCode(), "관리자 로그인 완료", null);
        }
        return new ResponseEntity(statusMessageDto, HttpStatus.OK);
    }
}
