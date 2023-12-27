package com.community.global.common;

public class RedisKey {
    public static String postViewKey(Long postId){
        return "post:" + postId + ":view_count";
    }

    public static String postLikesKey(Long postId) {
        return "post:" + postId + ":likes";
    }

    public static String userLikedPostsKey(Long postId, Long userId) {
        return "user:" + userId + ":liked_posts:" + postId;
    }
}
