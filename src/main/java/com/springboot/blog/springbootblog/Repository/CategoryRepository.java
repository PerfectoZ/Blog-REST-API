package com.springboot.blog.springbootblog.Repository;

import com.springboot.blog.springbootblog.Entity.Category;
import com.springboot.blog.springbootblog.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
