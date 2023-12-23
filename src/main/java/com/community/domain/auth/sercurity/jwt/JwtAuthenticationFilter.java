package com.community.domain.auth.sercurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sparta.springlv2.domain.auth.model.dto.LoginRequestDto;
import com.sparta.springlv2.domain.auth.model.entity.User;
import com.sparta.springlv2.domain.auth.sercurity.UserDetailsImpl;
import com.sparta.springlv2.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword()
                            ,null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        String token = jwtUtil.createToken(user.getEmail(), user.getRole());

        jwtUtil.addJwtToCookie(token, response);

        // JSON 응답 생성
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonResponse = objectMapper.createObjectNode();

        jsonResponse.put("token", token);
        jsonResponse.put("email", user.getEmail());
        jsonResponse.put("role", user.getRole().toString());


        // HTTP 응답 설정
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        AuthenticationException failed) throws IOException, ServletException {
        // JSON 응답 생성
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonResponse = objectMapper.createObjectNode();

        jsonResponse.put("msg", "아이디와 비밀번호가 맞지 않습니다.");

        // HTTP 응답 설정
        response.setStatus(400);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }
}