package com.community.domain.comment.api;

import com.community.domain.auth.sercurity.UserDetailsImpl;
import com.community.domain.comment.application.CommentService;
import com.community.domain.comment.model.dto.CommentRequestDto;
import com.community.domain.comment.model.dto.CommentResponseDto;
import com.community.global.common.ServiceResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/{postId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public CommentResponseDto createComment(@PathVariable final Long postId,
                                            @RequestBody @Valid  final CommentRequestDto requestDto,
                                            @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        return commentService.createComment(requestDto, userDetails.getUser(), postId);
    }

    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable final Long postId,
                                            @PathVariable final Long commentId,
                                            @RequestBody @Valid final CommentRequestDto requestDto,
                                            @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        return commentService.updateComment(postId, commentId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable final Long postId,
                                           @PathVariable final Long commentId,
                                           @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        commentService.deleteComment(postId, commentId, userDetails.getUser());
        return ResponseEntity.ok().body(ServiceResult.success("삭제 성공"));
    }
}
