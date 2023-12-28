package com.community.domain.post.model.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts {

    private List<PostResponseDto> posts = new ArrayList<>();

    public Posts(List<PostResponseDto> posts) {
        this.posts = posts;
    }
}
