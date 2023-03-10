package com.springboot.blog.springbootblog.Repository;

import com.springboot.blog.springbootblog.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(long userId);
    List<Post> findByCategoryId(Long categoryId);
}
