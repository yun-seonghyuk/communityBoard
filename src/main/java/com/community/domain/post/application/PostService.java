package com.community.domain.post.application;

import com.community.domain.auth.model.entity.User;
import com.community.domain.post.model.dto.request.PostRequestDto;
import com.community.domain.post.model.dto.response.PostResponseDto;
import com.community.domain.post.model.dto.response.Posts;

public interface PostService {

    PostResponseDto createPost(PostRequestDto requestDto, User user);

    Posts getAllPosts();

    Posts getAllLikeDescPosts();

    PostResponseDto getPost(Long id);

    PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user);

    void deletePost(Long id, User user);

    void likePost(Long postId, Long id);
}
