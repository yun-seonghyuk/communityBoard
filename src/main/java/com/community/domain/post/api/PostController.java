package com.community.domain.post.api;


import com.community.domain.auth.sercurity.UserDetailsImpl;
import com.community.domain.post.application.PostService;
import com.community.domain.post.model.dto.request.PostRequestDto;
import com.community.domain.post.model.dto.response.PostResponseDto;
import com.community.global.common.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody final PostRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok()
                .body(postService.createPost(requestDto, userDetails.getUser()));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @GetMapping("/posts/like")
    public ResponseEntity<?> getAllLikeDescPosts() {
        return ResponseEntity.ok().body(postService.getAllLikeDescPosts());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.getPost(id));
    }

    @PutMapping("/post/update/{id}")
    public PostResponseDto updatePost(@PathVariable Long id,
                                      @RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.updatePost(id, postRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.deletePost(id, userDetails.getUser());
        return ResponseEntity.ok()
                .body(ServiceResult.success("삭제가 완료되었습니다."));
    }

    @PostMapping("post/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.likePost(postId, userDetails.getUser().getId());
        return ResponseEntity.ok("Post liked successfully");
    }

}
