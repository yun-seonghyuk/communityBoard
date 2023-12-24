package com.community.domain.post.application;

import com.community.domain.auth.model.entity.User;
import com.community.domain.post.model.dto.PostRequestDto;
import com.community.domain.post.model.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(PostRequestDto requestDto, User user);

    List<PostResponseDto> getAllPosts();

    PostResponseDto getPost(Long id);

    PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user);

    void deletePost(Long id, User user);

}
