package com.springboot.blog.payload;

import lombok.Data;

@Data //getter setter toString via Lombok
public class CommentDto {

    private Long id;
    private String name;
    private String email;
    private String body;
}
