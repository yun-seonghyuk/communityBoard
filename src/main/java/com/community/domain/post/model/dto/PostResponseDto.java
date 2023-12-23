package com.community.domain.post.model.dto;

import com.community.domain.post.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long id;
    private String username;
    private String title;
    private String content;
    private Integer viewCount;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .createAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .createAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .commentList(post.getCommentList().stream()
                        .map(CommentResponseDto::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
