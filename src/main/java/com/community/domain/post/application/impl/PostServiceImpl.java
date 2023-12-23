package com.community.domain.post.application.impl;

import com.sparta.springlv2.domain.auth.model.entity.User;
import com.sparta.springlv2.domain.post.application.PostService;
import com.sparta.springlv2.domain.post.domain.dto.PostRequestDto;
import com.sparta.springlv2.domain.post.domain.dto.PostResponseDto;
import com.sparta.springlv2.domain.post.domain.entity.Post;
import com.sparta.springlv2.domain.post.exception.PostException;
import com.sparta.springlv2.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.sparta.springlv2.global.exception.ErrorCode.NOT_POST_BY_YOU;
import static com.sparta.springlv2.global.exception.ErrorCode.POST_NOT_FOUND;


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

        return PostResponseDto.from(post);
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
