package com.community.domain.post.repository;


import com.community.domain.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

//    @Query(value = "select p.id, p.title,p.content, " +
//            "p.user.username, p.createdAt, p.modifiedAt from" +
//            " Post p join fetch p.user")
}