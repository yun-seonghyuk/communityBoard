package com.community.domain.post.application.impl;


import com.community.domain.auth.model.entity.User;
import com.community.domain.post.application.PostService;
import com.community.domain.post.exception.PostException;
import com.community.domain.post.model.dto.PostRequestDto;
import com.community.domain.post.model.dto.PostResponseDto;
import com.community.domain.post.model.entity.Post;
import com.community.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.community.global.exception.ErrorCode.NOT_POST_BY_YOU;
import static com.community.global.exception.ErrorCode.POST_NOT_FOUND;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    @Override
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(Post.builder()
                .user(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build());

        return PostResponseDto.of(post);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponseDto getPost(Long id) {
        return PostResponseDto.from(findPostOrElseThrow(id));
    }

    @Transactional
    @Override
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = findPostOrElseThrow(id);
        checkAuthorship(post, user);
        post.postUpdate(postRequestDto);

        return PostResponseDto.of(post);
    }
    @Override
    public void deletePost(Long id, User user) {
        Post post = findPostOrElseThrow(id);
        checkAuthorship(post, user);
        postRepository.delete(post);
    }
    @Transactional
    @Override
    public void views(Long id) {
        Post post = findPostOrElseThrow(id);
        post.viewUpdate();
    }

    private Post findPostOrElseThrow(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new PostException(POST_NOT_FOUND));
    }

    private void checkAuthorship(Post post, User user) {
        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new PostException(NOT_POST_BY_YOU);
        }
    }


}
