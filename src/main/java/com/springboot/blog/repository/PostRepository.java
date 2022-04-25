package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// Separate Repository for each Entity.
// No need to add @Repository because JpaRepository already has it.
public interface PostRepository extends JpaRepository<Post, Long> {
    // <Entity type, PK type>
    // No need to write any code here lol.
}
