package com.springboot.blog.springbootblog.Service;

import com.springboot.blog.springbootblog.Entity.Comment;
import com.springboot.blog.springbootblog.Payload.CommentDto;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto commentDto);
    public List<CommentDto> getCommentsByPostId(Long postId);
    public CommentDto getCommentsByCommentId(Long postId, Long commentId);
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);
    public void deleteComment(Long postId, Long commentId);
}
