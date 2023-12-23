package com.community.global.advice;

import com.sparta.springlv2.domain.auth.exception.MemberException;
import com.sparta.springlv2.domain.post.exception.PostException;
import com.sparta.springlv2.global.common.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(PostException.class)
    public ResponseEntity<?> postExceptionHandler(PostException e){
        return ResponseEntity.ok()
                .body(ServiceResult.fail(e.getErrorCode()));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<?> memberExceptionHandler(MemberException e){
        return ResponseEntity.ok()
                .body(ServiceResult.fail(e.getErrorCode()));
    }
}
