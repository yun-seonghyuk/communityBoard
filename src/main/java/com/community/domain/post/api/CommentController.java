package com.community.domain.post.api;

import com.community.domain.auth.sercurity.UserDetailsImpl;
import com.community.domain.post.application.CommentService;
import com.community.domain.post.model.dto.CommentRequestDto;
import com.community.domain.post.model.dto.CommentResponseDto;
import com.community.global.common.ServiceResult;
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
    public CommentResponseDto createComment(@PathVariable Long postId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.createComment(requestDto, userDetails.getUser(), postId);
    }

    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long postId,
                                            @PathVariable Long commentId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.updateComment(postId, commentId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long postId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.deleteComment(postId, commentId, userDetails.getUser());
        return ResponseEntity.ok().body(ServiceResult.success("삭제 성공"));
    }
}
