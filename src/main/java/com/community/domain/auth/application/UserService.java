package com.community.domain.auth.application;


import com.community.domain.auth.model.dto.SignupRequestDto;

public interface UserService {
    void signup(SignupRequestDto requestDto);

}
