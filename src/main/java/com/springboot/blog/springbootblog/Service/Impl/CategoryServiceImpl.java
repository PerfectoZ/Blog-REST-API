package com.springboot.blog.springbootblog.Service.Impl;

import com.springboot.blog.springbootblog.Entity.Category;
import com.springboot.blog.springbootblog.Exceptions.ResourceNotFoundException;
import com.springboot.blog.springbootblog.Payload.CategoryDto;
import com.springboot.blog.springbootblog.Repository.CategoryRepository;
import com.springboot.blog.springbootblog.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedcategory = categoryRepository.save(category);
        return modelMapper.map(savedcategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category","Id",id)
        );
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category","Id",id)
        );
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category saved = categoryRepository.save(category);
        return modelMapper.map(saved, CategoryDto.class);
    }

    @Override
    public String deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category","Id",id)
        );
        categoryRepository.delete(category);
        return "Deleted Successfully";
    }


}
