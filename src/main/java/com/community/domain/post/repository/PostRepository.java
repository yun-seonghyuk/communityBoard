package com.community.domain.post.repository;


import com.community.domain.post.model.entity.Post;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllByOrderByViewCountDesc();
}
