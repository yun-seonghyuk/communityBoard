package com.community.domain.post.exception;

import com.sparta.springlv2.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostException extends RuntimeException{
    private ErrorCode errorCode;

}
