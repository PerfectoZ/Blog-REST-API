package com.springboot.blog.springbootblog.Service;
import com.springboot.blog.springbootblog.Payload.PostDto;
import com.springboot.blog.springbootblog.Payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, boolean desc);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePostById(Long id);
}
