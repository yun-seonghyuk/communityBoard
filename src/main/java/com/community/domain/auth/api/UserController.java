package com.community.domain.auth.api;


import com.community.domain.auth.application.UserService;
import com.community.domain.auth.model.dto.SignupRequestDto;
import com.community.global.common.ServiceResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid final SignupRequestDto requestDto){
        userService.signup(requestDto);
        return ResponseEntity.ok()
                .body(ServiceResult.success("회원가입을 축하드립니다."));
    }


}
