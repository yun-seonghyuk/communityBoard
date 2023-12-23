package com.community.domain.post.application.impl;

import com.sparta.springlv2.domain.auth.model.entity.User;
import com.sparta.springlv2.domain.post.application.CommentService;
import com.sparta.springlv2.domain.post.domain.dto.CommentRequestDto;
import com.sparta.springlv2.domain.post.domain.dto.CommentResponseDto;
import com.sparta.springlv2.domain.post.domain.entity.Comment;
import com.sparta.springlv2.domain.post.domain.entity.Post;
import com.sparta.springlv2.domain.post.exception.PostException;
import com.sparta.springlv2.domain.post.repository.CommentRepository;
import com.sparta.springlv2.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.springlv2.global.exception.ErrorCode.COMMENT_NOT_FOUND;
import static com.sparta.springlv2.global.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user, Long id) {
        Post post = findPost(id);

        Comment comment = commentRepository.save(Comment.builder()
                .user(user)
                .post(post)
                .content(requestDto.getContent())
                .build());

        return CommentResponseDto.of(comment);
    }

    @Transactional
    @Override
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestDto, User user) {
        checkPostExistence(postId);
        Comment comment = checkCommentExistence(commentId);
        checkAuthorship(comment, user);

        comment.update(requestDto);

        return CommentResponseDto.of(comment);
    }
    @Override
    public void deleteComment(Long postId, Long commentId, User user) {
        checkPostExistence(postId);
        Comment comment = checkCommentExistence(commentId);
        checkAuthorship(comment, user);

        commentRepository.delete(comment);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostException(POST_NOT_FOUND)
        );
    }
    private void checkPostExistence(Long id) {
        postRepository.findById(id).orElseThrow(
                () -> new PostException(POST_NOT_FOUND)
        );
    }
    private Comment checkCommentExistence(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new PostException(COMMENT_NOT_FOUND)
        );
    }
    private void checkAuthorship(Comment comment, User user) {
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("회원님이 작성하신 메모가 아닙니다");
        }
    }

}
