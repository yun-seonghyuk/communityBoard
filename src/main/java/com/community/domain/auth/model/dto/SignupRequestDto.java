package com.community.domain.auth.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDto {

    @Size(min=4, max = 10, message = "4자 이상 10자 이하여야 합니다.")
    @Pattern(regexp = "^[a-z0-9]*$")
    private String username;

    @Size(min=8, max = 15, message = "8자 이상 15자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String password;

    @Email
    private String email;

    private boolean admin = false;
    private String adminToken = "";
}
