package com.community.domain.post.application;

import com.sparta.springlv2.domain.auth.model.entity.User;
import com.sparta.springlv2.domain.post.domain.dto.CommentRequestDto;
import com.sparta.springlv2.domain.post.domain.dto.CommentResponseDto;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto requestDto, User user, Long id);
    CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestDto, User user);
    void deleteComment(Long postId, Long commentId, User user);
}
