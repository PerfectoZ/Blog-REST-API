package com.springboot.blog.springbootblog.Repository;

import com.springboot.blog.springbootblog.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
