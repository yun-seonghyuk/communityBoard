package com.community.domain.comment.model.entity;

import com.community.domain.auth.model.entity.User;
import com.community.domain.comment.model.dto.CommentRequestDto;
import com.community.domain.post.model.entity.Post;
import com.community.global.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    @ColumnDefault("0")
    private Integer likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.setModifiedAt(LocalDateTime.now());
    }
}



