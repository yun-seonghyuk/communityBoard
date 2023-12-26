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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
//    @Cacheable( value = "allPosts")
//    @Cacheable(cacheNames = "posts", key = "'all'")
    @Override
    public Posts getAllPosts() {
        List<PostResponseDto> posts = postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponseDto::from)
                .toList();

        return new Posts(posts);
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
        checkPostAuthor(post, user);
        post.postUpdate(postRequestDto);

        return PostResponseDto.of(post);
    }

//    public void addViewCntToRedis(Long productId) {
//        String key = "productViewCnt::"+productId;
//        //hint 캐시에 값이 없으면 레포지토리에서 조회 있으면 값을 증가시킨다.
//        ValueOperations valueOperations = redisCacheConfig.redisTemplate().opsForValue();
//        if(valueOperations.get(key)==null)
//            valueOperations.set(
//                    key,
//                    String.valueOf(productRepository.findProductViewCnt(productId)),
//                    Duration.ofMinutes(5));
//        else
//            valueOperations.increment(key);
//        log.info("value:{}",valueOperations.get(key));
//    }

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
