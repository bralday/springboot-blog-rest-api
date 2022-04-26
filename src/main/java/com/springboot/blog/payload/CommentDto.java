package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data //getter setter toString via Lombok
public class CommentDto {

    private Long id;

    @NotEmpty(message = "Name must not be empty.")
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @NotEmpty(message = "Email must not be empty.")
    @Email(message = "Must be a valid email.")
    private String email;

    @NotEmpty(message = "Body must not be empty.")
    private String body;
}
