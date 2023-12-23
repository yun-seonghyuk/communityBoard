package com.community.domain.auth.api;


import com.community.domain.auth.application.impl.UserServiceImpl;
import com.community.domain.auth.model.dto.SignupRequestDto;
import com.community.global.common.ServiceResult;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto requestDto){
        userService.signup(requestDto);
        return ResponseEntity.ok().body(ServiceResult.success("회원가입을 축하드립니다."));
    }

}
