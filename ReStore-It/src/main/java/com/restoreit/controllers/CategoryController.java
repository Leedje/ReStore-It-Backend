package com.restoreit.controllers;

import com.restoreit.dtos.CategoryDTO;
import com.restoreit.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> GetAllCategories(){
        return ResponseEntity.ok(categoryService.GetAllCategories());
    }
}
