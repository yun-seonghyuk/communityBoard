package com.community.domain.auth.exception;

import com.sparta.springlv2.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException {
    ErrorCode errorCode;

}
