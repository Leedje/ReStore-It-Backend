package com.restoreit.services;

import com.restoreit.dtos.CategoryDTO;
import com.restoreit.mappers.CategoryMapper;
import com.restoreit.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDTO> GetAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::CategoryToDTO).collect(Collectors.toList());
    }

    public boolean CreateCategory(CategoryDTO categoryDTO) {
        return categoryRepository.save(categoryMapper.DTOtoCategory(categoryDTO)) != null;
    }
}
