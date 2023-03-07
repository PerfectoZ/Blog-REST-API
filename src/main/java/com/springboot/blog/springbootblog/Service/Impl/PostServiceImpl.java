package com.springboot.blog.springbootblog.Service.Impl;

import com.springboot.blog.springbootblog.Entity.Post;
import com.springboot.blog.springbootblog.Entity.User;
import com.springboot.blog.springbootblog.Exceptions.BlogAPIException;
import com.springboot.blog.springbootblog.Exceptions.ResourceNotFoundException;
import com.springboot.blog.springbootblog.Payload.PostDto;
import com.springboot.blog.springbootblog.Payload.PostResponse;
import com.springboot.blog.springbootblog.Repository.PostRepository;
import com.springboot.blog.springbootblog.Repository.UserRepository;
import com.springboot.blog.springbootblog.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;
    private UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        // convert DTO to Entity
        Post post = mapToEntity(postDto);
        User creator = GetUser();
        post.setUser(creator);
        Post newPost = postRepository.save(post);

        // convert entity to dto
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, boolean desc) {

        Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content =  listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

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
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        User creator = post.getUser();
        User loggedin = GetUser();

        if(creator.getId()!=loggedin.getId())
            throw new BlogAPIException("Not Allowed to Edit Other's Post", HttpStatus.UNAUTHORIZED, "");

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        User creator = post.getUser();
        User loggedin = GetUser();

        if(creator.getId()!=loggedin.getId())
            throw new BlogAPIException("Not Allowed to Delete Other's Post", HttpStatus.UNAUTHORIZED, "");
        postRepository.delete(post);
    }

    //convert Entity to DTO
    private PostDto mapToDTO(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        /*PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());*/
        return postDto;
    }

    //convert DTO to Entity
    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        /*Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/
        return post;
    }

    private User GetUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        System.out.println(auth.getName());
        return userRepository.findUserByUsernameOrEmail(auth.getName(), auth.getName()).orElse(null);
    }

}
