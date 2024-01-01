package com.community.domain.post.application.impl;


import com.community.domain.auth.model.entity.User;
import com.community.domain.post.repository.PostRepository;
import com.community.domain.post.application.PostService;
import com.community.domain.post.exception.PostException;
import com.community.domain.post.model.dto.request.PostRequestDto;
import com.community.domain.post.model.dto.response.PostResponseDto;
import com.community.domain.post.model.dto.response.Posts;
import com.community.domain.post.model.entity.Post;
import com.community.global.common.RedisKey;
import com.community.global.config.RedisCacheConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.community.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final RedisCacheConfig redisCacheConfig;

    @Override
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(Post.createPost(user, requestDto));
        return PostResponseDto.of(post);
    }

    @Transactional(readOnly = true)
    @Override
    public Posts getAllPosts() {
        List<PostResponseDto> posts = postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponseDto::allPost)
                .toList();

        return new Posts(posts);
    }

    @Transactional(readOnly = true)
    @Override
    public Posts getAllLikeDescPosts() {
        List<PostResponseDto> posts = postRepository.findAllByOrderByViewCountDesc()
                .stream()
                .map(PostResponseDto::allPost)
                .toList();

        return new Posts(posts);
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Post post = findPostOrElseThrow(id);
        postViewCount(id);
        return PostResponseDto.from(post, getLikesCountForPost(id));
    }

    //    @Async
    public void postViewCount(Long postId) {
        redisCacheConfig.redisTemplate()
                .opsForValue()
                .increment(RedisKey.postViewKey(postId), 1);
    }

    public Integer getLikesCountForPost(Long postId) {
        String likes = redisCacheConfig.redisTemplate().opsForValue()
                .get(RedisKey.postLikesKey(postId));
        return likes != null ? Integer.parseInt(likes) : 0;
    }

    @Override
    @Async
    public void likePost(Long postId, Long userId) {

        // 사용자가 이미 해당 게시물에 대해 좋아요를 눌렀는지 확인
        try {

            Boolean alreadyLiked = redisCacheConfig.redisTemplate()
                    .opsForSet()
                    .isMember(RedisKey.userLikedPostsKey(userId, postId), String.valueOf(postId));

            if (alreadyLiked != null && alreadyLiked) {
                // 이미 좋아요를 누른 경우 좋아요 취소
                redisCacheConfig.redisTemplate().opsForValue()
                        .decrement(RedisKey.postLikesKey(postId));
                redisCacheConfig.redisTemplate().opsForSet()
                        .remove(RedisKey.userLikedPostsKey(userId, postId), String.valueOf(postId));
            } else {
                // 아직 좋아요를 누르지 않은 경우 좋아요 추가
                redisCacheConfig.redisTemplate().opsForValue().increment(RedisKey.postLikesKey(postId));
                redisCacheConfig.redisTemplate().opsForSet()
                        .add(RedisKey.userLikedPostsKey(userId, postId), String.valueOf(postId));
            }

        } catch (PostException e) {
            throw new PostException(FAILED__TO_PROCESS_LIKE_FOR_POST_ID);
        }

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
