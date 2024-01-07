package com.community.global.util;

import com.community.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenException extends RuntimeException {
    private ErrorCode errorCode;
}
