package com.community.domain.post.application;


import com.community.domain.auth.model.entity.User;
import com.community.domain.post.model.dto.CommentRequestDto;
import com.community.domain.post.model.dto.CommentResponseDto;


public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto requestDto, User user, Long id);

    CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestDto, User user);

    void deleteComment(Long postId, Long commentId, User user);
}
