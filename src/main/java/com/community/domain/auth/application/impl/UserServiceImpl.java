package com.community.domain.auth.application.impl;

import com.community.domain.auth.application.UserService;
import com.community.domain.auth.exception.MemberException;
import com.community.domain.auth.model.dto.SignupRequestDto;
import com.community.domain.auth.model.entity.User;
import com.community.domain.auth.model.type.UserRoleEnum;
import com.community.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.community.global.exception.ErrorCode.DUPLICATE_EMAIL;
import static com.community.global.exception.ErrorCode.INVALID_ADMIN_PASSWORD;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Override
    public void signup(SignupRequestDto requestDto) {
        Optional<User> checkEmail = userRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new MemberException(DUPLICATE_EMAIL);
        }
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new MemberException(INVALID_ADMIN_PASSWORD);
            }
            role = UserRoleEnum.ADMIN;
        }

        userRepository.save(User.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(role)
                .build());

    }

}
