package com.community.domain.post.application.impl;


import com.community.domain.auth.model.entity.User;
import com.community.domain.post.application.PostService;
import com.community.domain.post.exception.PostException;
import com.community.domain.post.model.dto.PostRequestDto;
import com.community.domain.post.model.dto.PostResponseDto;
import com.community.domain.post.model.dto.Posts;
import com.community.domain.post.model.entity.Post;
import com.community.domain.post.repository.PostRepository;
import com.community.global.config.RedisCacheConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.community.global.exception.ErrorCode.NOT_POST_BY_USER;
import static com.community.global.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final RedisCacheConfig redisCacheConfig;

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
    public Posts getAllPosts() {

        List<PostResponseDto> posts = postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponseDto::from)
                .toList();

        return new Posts(posts);
    }

    @Transactional
    @Override
    public PostResponseDto getPost(Long id) {
        Post post = findPostOrElseThrow(id);
        PostViewCount(id);
        return PostResponseDto.from(post);
    }

    @Transactional
    @Override
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = findPostOrElseThrow(id);
        checkPostAuthor(post, user);
        post.postUpdate(postRequestDto);

        return PostResponseDto.of(post);
    }

    @Override
    public void PostViewCount(Long postId) {
        String viewCountKey = "post:" + postId + ":view_count";
        redisCacheConfig.redisTemplate().opsForValue().increment(viewCountKey);
    }

    // Write-Back
    @Override
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    @Transactional
    public void writeBackToDatabase() {
        // 조회수 Write-Back
        Set<String> keys = redisCacheConfig.redisTemplate().keys("post:*:view_count");
        for (String key : Objects.requireNonNull(keys)) {
            Long postId = Long.parseLong(key.split(":")[1]);
            String viewCount = redisCacheConfig.redisTemplate().opsForValue().get(key);

            postRepository.findById(postId).ifPresent(post ->
                    post.viewCountUpdate(Integer.parseInt(Objects.requireNonNull(viewCount))));
        }
    }


    @Override
    public void deletePost(Long id, User user) {
        Post post = findPostOrElseThrow(id);
        checkPostAuthor(post, user);
        postRepository.delete(post);
    }

    private Post findPostOrElseThrow(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostException(POST_NOT_FOUND));
    }

    // 게시물 작성자 존재여부 확인
    private void checkPostAuthor(Post post, User user) {
        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new PostException(NOT_POST_BY_USER);
        }
    }


}
