package com.community.domain.auth.api;

import com.community.domain.auth.application.UserService;
import com.community.domain.auth.model.dto.SignupRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void successCreateUser() throws JsonProcessingException {
        //given
        SignupRequestDto requestDto = new SignupRequestDto();

        doNothing().when(userService).signup(any());

        // AccountService의 signup 메소드가 성공적으로 호출되도록 가짜 응답 설정

        // 가짜 요청 보내기 및 응답 확인
//        mockMvc.perform(post("/api/user/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isOk());

    }

}