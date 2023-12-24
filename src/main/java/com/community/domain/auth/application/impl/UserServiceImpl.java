package com.community.domain.auth.application.impl;

import com.community.domain.auth.application.UserService;
import com.community.domain.auth.exception.MemberException;
import com.community.domain.auth.model.dto.SignupRequestDto;
import com.community.domain.auth.model.entity.User;
import com.community.domain.auth.model.type.UserRole;
import com.community.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        emailDuplicateCheck(requestDto.getEmail());
        UserRole role = userRoleCheck(requestDto);

        userRepository.save(User.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(role)
                .build());
    }

    private void emailDuplicateCheck(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new MemberException(DUPLICATE_EMAIL);
                });
    }

    private UserRole userRoleCheck(SignupRequestDto requestDto) {
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new MemberException(INVALID_ADMIN_PASSWORD);
            }
            return UserRole.ADMIN;
        }
        return UserRole.USER;
    }



}
