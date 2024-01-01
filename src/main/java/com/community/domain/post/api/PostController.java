package com.community.domain.post.api;


import com.community.domain.auth.sercurity.UserDetailsImpl;
import com.community.domain.post.application.PostService;
import com.community.domain.post.model.dto.request.PostRequestDto;
import com.community.domain.post.model.dto.response.PostResponseDto;
import com.community.global.common.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
                                        @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        return ResponseEntity.ok()
                .body(postService.createPost(requestDto, userDetails.getUser()));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(@PageableDefault(page = 0, size = 10,
            sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(postService.getAllPosts(pageable));
    }

    @GetMapping("/posts/like")
    public ResponseEntity<?> getAllLikeDescPosts(@PageableDefault(page = 0, size = 10,
            sort = "likeCount", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(postService.getAllLikeDescPosts(pageable));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable final Long id) {
        return ResponseEntity.ok().body(postService.getPost(id));
    }

    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable final Long id,
                                      @RequestBody final PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        return postService.updatePost(id, postRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable final Long id,
                                        @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        postService.deletePost(id, userDetails.getUser());
        return ResponseEntity.ok()
                .body(ServiceResult.success("delete success!"));
    }

    @PostMapping("post/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable final Long postId,
                                      @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        postService.likePost(postId, userDetails.getUser().getId());
        return ResponseEntity.ok("Post liked successfully");
    }

}
