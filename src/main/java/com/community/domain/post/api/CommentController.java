package com.community.domain.post.api;

import com.sparta.springlv2.domain.auth.sercurity.UserDetailsImpl;
import com.sparta.springlv2.domain.post.application.impl.CommentServiceImpl;
import com.sparta.springlv2.domain.post.domain.dto.CommentRequestDto;
import com.sparta.springlv2.domain.post.domain.dto.CommentResponseDto;
import com.sparta.springlv2.global.common.ServiceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/{postId}")
@Slf4j
public class CommentController {


    private final CommentServiceImpl commentService;

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
