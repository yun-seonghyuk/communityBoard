package com.community.global.advice;

import com.community.domain.auth.exception.MemberException;
import com.community.domain.post.exception.PostException;
import com.community.global.common.ServiceResult;
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
