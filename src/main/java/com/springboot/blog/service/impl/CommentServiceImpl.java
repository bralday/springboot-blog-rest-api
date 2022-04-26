package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    //no need @Autowire kasi isa lang naman constructor
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        // Convert DTO to entity using private method below.
        Comment comment = mapToEntity(commentDto);

        // Retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        //setPost to comment entity
        comment.setPost(post);

        // Save to database using .save()
        Comment newComment = this.commentRepository.save(comment);

        //convert entity (post) to DTO using private method below.
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        // Retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if comment belongs to the post, return exception if not.
        if(!comment.getPost().getId().equals(postId)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to Post" );
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        // Retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if comment belongs to the post, return exception if not.
        if(!comment.getPost().getId().equals(postId)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to Post" );
        }

        // Tutorial also set the email and name, but I don't think that's how it should be irl.
        comment.setBody(commentDto.getBody());

        //save comment (will save to db too)
        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        // Retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if comment belongs to the post, return exception if not.
        if(!comment.getPost().getId().equals(postId)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to Post" );
        }

        commentRepository.deleteById(commentId);
    }

    // Convert DTO to entity
    private Comment mapToEntity (CommentDto commentDto){
        Comment comment = mapper.map(commentDto,Comment.class);

        /*Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());*/

        return comment;
    }

    //convert entity (post) to DTO
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        /*CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());*/

        return commentDto;
    }
}
