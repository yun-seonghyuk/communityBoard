package com.community.domain.post.exception;

import com.community.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostException extends RuntimeException{
    private ErrorCode errorCode;

}
