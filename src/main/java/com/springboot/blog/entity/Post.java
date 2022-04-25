package com.springboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data // Lombok - Generates getters, setters, and toString (via annotations)
@AllArgsConstructor // Generates all args constructor via Lombok
@NoArgsConstructor // Generates no args constructor via Lombok

@Entity
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})} // Indicates Title must be unique.
)
public class Post {

    // When application is run, creates table in database if not existing yet.

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;
}
