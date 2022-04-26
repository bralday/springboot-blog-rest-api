package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data //getter setter toString via Lombok
public class PostDto {

    private Long id;

    //Add validations via validation.constraints for api requests
    @NotEmpty(message = "Title must not be empty.")
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty(message = "Description must not be empty.")
    @Size(min = 10, message = "Post title should have at least 10 characters")
    private String description;

    @NotEmpty(message = "Content must not be empty.")
    private String content;
    private Set<CommentDto> comments;
}
