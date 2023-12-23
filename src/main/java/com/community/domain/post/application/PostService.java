package com.community.domain.post.application;

import com.sparta.springlv2.domain.auth.model.entity.User;
import com.sparta.springlv2.domain.post.domain.dto.PostRequestDto;
import com.sparta.springlv2.domain.post.domain.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(PostRequestDto requestDto, User user);
    List<PostResponseDto> getAllPosts();
    PostResponseDto getPost(Long id);
    PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user);
    void deletePost(Long id, User user);
}
