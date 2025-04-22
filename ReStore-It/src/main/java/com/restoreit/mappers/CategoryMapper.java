package com.restoreit.mappers;

import org.mapstruct.Mapper;
import com.restoreit.models.Category;
import com.restoreit.dtos.CategoryDTO;
import org.mapstruct.factory.Mappers;

@Mapper (componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    Category DTOtoCategory(CategoryDTO categoryDTO);
    CategoryDTO CategoryToDTO(Category category);
}
