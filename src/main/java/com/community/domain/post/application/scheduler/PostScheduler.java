package com.community.domain.post.application.scheduler;

import com.community.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final PostRepository postRepository;


    // Write-Back
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
//    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void ViewCountAndLikeCountDBUpdate() {
        Set<String> keys = redisTemplate.keys("post:*:view_count");
        for (String key : Objects.requireNonNull(keys)) {
            Long postId = Long.parseLong(key.split(":")[1]);
            String viewCount = redisTemplate.opsForValue().get(key);

            postRepository.findById(postId).ifPresent(view ->
                    view.viewCountUpdate(Integer.parseInt(Objects.requireNonNull(viewCount))));
        }

        Set<String> likeKeys = redisTemplate.keys("post:*:likes");
        for (String key : Objects.requireNonNull(likeKeys)) {
            Long postId = Long.parseLong(key.split(":")[1]);
            String likeCount = redisTemplate.opsForValue().get(key);
            postRepository.findById(postId).ifPresent(like ->
                    like.likeCountUpdate(Integer.parseInt(Objects.requireNonNull(likeCount))));
        }
    }

}
