package com.restoreit.mappers;

import org.mapstruct.Mapper;
import com.restoreit.dtos.ProductDTO;
import com.restoreit.models.Product;
import org.mapstruct.factory.Mappers;

@Mapper (uses = CategoryMapper.class)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDTO ProductToDTO(Product product);
    Product DTOToProduct(ProductDTO productDTO);
}
