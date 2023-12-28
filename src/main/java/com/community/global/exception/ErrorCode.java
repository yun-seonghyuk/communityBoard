package com.community.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당게시글이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST,"해당 댓글이 없습니다."),
    NOT_POST_BY_USER(HttpStatus.BAD_REQUEST, "회원님이 작성하신 메모가 아닙니다."),
    FAILED__TO_PROCESS_LIKE_FOR_POST_ID(HttpStatus.BAD_REQUEST,"지원하지 않는 postId 입니다."),


    NOT_FOUND_EMAIL(HttpStatus.BAD_REQUEST, "등록되지 않은 이메일입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일 입니다."),
    INVALID_ADMIN_PASSWORD(HttpStatus.BAD_REQUEST, "관리자 암호가 틀려 등록이 불가합니다.");

    private final HttpStatus statusCode;
    private final String message;
}
