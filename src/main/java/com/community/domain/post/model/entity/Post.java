package com.community.domain.post.model.entity;

import com.community.domain.auth.model.entity.User;
import com.community.domain.post.model.dto.PostRequestDto;
import com.community.global.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "post")
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column
    @ColumnDefault("0")
    private Integer viewCount;

    @Column
    @ColumnDefault("0")
    private Integer likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private List<Comment> commentList;

    public void postUpdate(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public void viewUpdate() {
        this.viewCount++;
    }
}
