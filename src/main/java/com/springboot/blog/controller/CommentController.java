package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.CommentResponse;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.blog.utils.AppConstants.*;
import static com.springboot.blog.utils.AppConstants.DEFAULT_PAGE_SORT_DIRECTION;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    private PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    // Create new comment
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value = "postId") long postId,
            @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    // Get all posts with pagination and sorting support
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(
            @PathVariable(value = "postId") long postId
    ){
        return commentService.getCommentsByPostId(postId);
    }

}
