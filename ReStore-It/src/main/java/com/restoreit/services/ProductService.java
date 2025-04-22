package com.restoreit.services;

import com.restoreit.dtos.ProductDTO;
import com.restoreit.mappers.ProductMapper;
import com.restoreit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO GetProductByID(Integer id){
        return productMapper.ProductToDTO(productRepository.findById(id).get());
    }

    public boolean CreateProduct(ProductDTO productDto){
        return productRepository.save( productMapper.DTOToProduct(productDto)) != null;
    }

    public List<ProductDTO> GetAllProducts(){
        return productRepository.findAll().stream().map(productMapper::ProductToDTO).collect(Collectors.toList());
    }

    public void DeleteProduct(Integer id){
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }
    }

    public void UpdateProduct(ProductDTO productDto){
        productRepository.save(productMapper.DTOToProduct(productDto));
    }
}
