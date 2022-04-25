package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // So we can autowire this class to other class
public class PostServiceImpl implements PostService {


    PostRepository postRepository;

    // @Autowired - Can be omitted IF class is configured as a springbean and only has 1 constructor.
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto createPost(PostDto postDto) {

        // Convert DTO to entity using private method below.
        Post post = mapToEntity(postDto);

        // Save to database using .save()
        Post newPost = this.postRepository.save(post);

        //convert entity (post) to DTO using private method below.
        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // Determine whether asc or desc
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

        // Create pageable instance
        // org.springframework.data.domain.Pageable
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        // Return a page of posts using pageable
        Page<Post> posts = postRepository.findAll(pageable);

        // Get content from page object
        List<Post> listOfPost = posts.getContent();

        List<PostDto> content =  listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id));
        PostDto responseDto = mapToDto(post);

        return responseDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post", "ID", id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        // Save to database using .save()
        // IMPORTANT OR ELSE IT WONT REFLECT SA GETMAPPING
        Post newPost = this.postRepository.save(post);
        return mapToDto(newPost);

    }

    @Override
    public void deletePostById(long id) {
        boolean b = postRepository.existsById(id);

        if(!b){
            throw new ResourceNotFoundException("Post", "ID", id);
        }else{
            postRepository.deleteById(id);
        }

    }

    // Convert DTO to entity
    private Post mapToEntity (PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        return post;
    }

    //convert entity (post) to DTO
    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }
}
