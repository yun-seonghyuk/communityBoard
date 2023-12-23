package com.community.domain.auth.application;

import com.sparta.springlv2.domain.auth.model.dto.SignupRequestDto;

public interface UserService {
    void signup(SignupRequestDto requestDto);

}
