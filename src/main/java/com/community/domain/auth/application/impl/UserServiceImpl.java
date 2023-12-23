package com.community.domain.auth.application.impl;

import com.sparta.springlv2.domain.auth.application.UserService;
import com.sparta.springlv2.domain.auth.exception.MemberException;
import com.sparta.springlv2.domain.auth.model.dto.SignupRequestDto;
import com.sparta.springlv2.domain.auth.model.entity.User;
import com.sparta.springlv2.domain.auth.model.type.UserRoleEnum;
import com.sparta.springlv2.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.springlv2.global.exception.ErrorCode.DUPLICATE_EMAIL;
import static com.sparta.springlv2.global.exception.ErrorCode.INVALID_ADMIN_PASSWORD;

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
