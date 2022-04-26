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

    // Get all comments of post
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(
            @PathVariable(value = "postId") long postId
    ){
        return commentService.getCommentsByPostId(postId);
    }

    // Get specific comment by id
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long id
    ){
        return new ResponseEntity<>(commentService.getCommentById(postId,id),HttpStatus.OK);
    }

    // Update comment
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long id,
            @RequestBody CommentDto commentDto
    ){
        return new ResponseEntity<>(commentService.updateComment(postId,id,commentDto), HttpStatus.OK);
    }

    // Delete comment by id
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long id){
        commentService.deleteComment(postId,id);

        return new ResponseEntity<>("Comment has been deleted successfully!", HttpStatus.OK);
    }
}
