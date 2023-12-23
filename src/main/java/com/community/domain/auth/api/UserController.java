package com.community.domain.auth.api;

import com.sparta.springlv2.domain.auth.application.impl.UserServiceImpl;
import com.sparta.springlv2.domain.auth.model.dto.SignupRequestDto;
import com.sparta.springlv2.global.common.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto){
        userService.signup(requestDto);
        return ResponseEntity.ok().body(ServiceResult.success("회원가입을 축하드립니다."));
    }

}
