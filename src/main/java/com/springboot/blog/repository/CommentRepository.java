package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Separate Repository for each Entity.
// No need to add @Repository because JpaRepository already has it.
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Implement custom method for finding comments by post id
    List<Comment> findByPostId(long postId);
}
