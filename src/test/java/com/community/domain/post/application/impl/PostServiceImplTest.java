package com.community.domain.post.application.impl;

import com.community.domain.post.application.PostService;
import com.community.global.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.data.redis.port=6379", // Redis 포트 설정 (실제 포트에 맞게 변경)
        "spring.data.redis.host=localhost" // Redis 호스트 설정 (실제 호스트에 맞게 변경)
})
class PostLikeConcurrencyTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PostService postService; // 사용자 정의 포스트 서비스 클래스

    @Test
    public void testLikePostConcurrency() throws InterruptedException {
        int numThreads = 2; // 동시에 실행될 스레드 수
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        Long postId = 123L; // 테스트할 포스트 ID
        Long userId = 456L; // 테스트할 사용자 ID

        for (int i = 0; i < numThreads; i++) {
            executorService.execute(() -> {
                postService.likePost(postId, userId);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        // 동시성 테스트 이후에 좋아요 수 확인
        int postLikes = Integer.parseInt(
                Objects.requireNonNull(redisTemplate.opsForValue().get(RedisUtil.postLikesKey(postId))));
        System.out.println("Total likes after concurrency test: " + postLikes);
    }
}