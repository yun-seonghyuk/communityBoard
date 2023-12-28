package com.community.domain.auth.model.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto{

    private String email;
    private String password;
}
