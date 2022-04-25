package com.springboot.blog.payload;

import lombok.Data;

@Data //getter setter toString via Lombok
public class PostDto {

    private Long id;
    private String title;
    private String description;
    private String content;
}
