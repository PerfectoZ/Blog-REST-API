package com.springboot.blog.springbootblog.Service;

import com.springboot.blog.springbootblog.Payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategory();
    CategoryDto updateCategory(CategoryDto categoryDto, Long id);
    String deleteCategoryById(Long id);
}
