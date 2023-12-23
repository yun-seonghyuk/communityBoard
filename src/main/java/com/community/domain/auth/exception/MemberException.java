package com.community.domain.auth.exception;

import com.community.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException {
    ErrorCode errorCode;

}
