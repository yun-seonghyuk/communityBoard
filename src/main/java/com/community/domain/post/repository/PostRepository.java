package com.community.domain.post.repository;


import com.community.domain.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByOrderByCreatedAtDesc();
}
