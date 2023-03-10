package com.springboot.blog.springbootblog.Controller;

import com.springboot.blog.springbootblog.Entity.Post;
import com.springboot.blog.springbootblog.Payload.PostDto;
import com.springboot.blog.springbootblog.Payload.PostResponse;
import com.springboot.blog.springbootblog.Service.Impl.PostServiceImpl;
import com.springboot.blog.springbootblog.Service.PostService;
import com.springboot.blog.springbootblog.Utils.AppConstants;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = AppConstants.DEFAULT_DESC, required = false) boolean desc
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, desc);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long id) {
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/createdBy/{username}")
    public List<PostDto> getPostByUser(@PathVariable String username) {
        return postService.getPostByUsername(username);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostsByCategory(id));
    }
}
