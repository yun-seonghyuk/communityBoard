package com.community.domain.post.api;


import com.community.domain.auth.sercurity.UserDetailsImpl;
import com.community.domain.post.application.impl.PostServiceImpl;
import com.community.domain.post.model.dto.PostRequestDto;
import com.community.domain.post.model.dto.PostResponseDto;
import com.community.global.common.ServiceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok()
                .body(postService.createPost(requestDto, userDetails.getUser()));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/post/update/{id}")
    public PostResponseDto updatePost(@PathVariable Long id,
                                      @RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.updatePost(id, postRequestDto, userDetails.getUser()) ;
    }

    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.deletePost(id, userDetails.getUser());
        return ResponseEntity.ok()
                .body(ServiceResult.success("삭제가 완료되었습니다."));
    }
}
