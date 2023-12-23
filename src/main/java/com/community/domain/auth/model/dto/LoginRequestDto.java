package com.community.domain.auth.model.dto;

import lombok.Data;

@Data
public class LoginRequestDto{

    private String email;
    private String password;
}
