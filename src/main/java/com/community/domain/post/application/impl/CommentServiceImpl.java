package com.community.domain.post.application.impl;

import com.community.domain.auth.model.entity.User;
import com.community.domain.post.application.CommentService;
import com.community.domain.post.model.dto.CommentRequestDto;
import com.community.domain.post.model.dto.CommentResponseDto;
import com.community.domain.post.model.entity.Comment;
import com.community.domain.post.model.entity.Post;
import com.community.domain.post.exception.PostException;
import com.community.domain.post.repository.CommentRepository;
import com.community.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.community.global.exception.ErrorCode.COMMENT_NOT_FOUND;
import static com.community.global.exception.ErrorCode.POST_NOT_FOUND;


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
