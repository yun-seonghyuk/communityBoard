package com.community.domain.post.repository;


import com.community.domain.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN FETCH p.commentList c "+
            "LEFT JOIN FETCH c.user u")
    List<Post> findAllByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN FETCH p.commentList c "+
            "LEFT JOIN FETCH c.user u")
    List<Post> findAllByOrderByViewCountDesc();
}
