package com.springboot.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Data // Lombok - Generates getters, setters, and toString (via annotations)
@AllArgsConstructor // Generates all args constructor via Lombok
@NoArgsConstructor // Generates no args constructor via Lombok
@Getter // added these 2 instead of @Data to prevent stackoverflow error caused by toString()
@Setter

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) // post field in Comment.class
    private Set<Comment> comments = new HashSet<>(); // Set so no duplicate entries
}
