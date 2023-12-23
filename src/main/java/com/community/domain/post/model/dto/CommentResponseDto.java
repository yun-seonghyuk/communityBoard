package com.community.domain.post.model.dto;

import com.community.domain.post.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;
    private String username;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .username(comment.getPost().getUser().getUsername())
                .content(comment.getContent())
                .createAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
