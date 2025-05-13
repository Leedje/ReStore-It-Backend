package com.restoreit.services;

import com.restoreit.dtos.ProductDTO;
import com.restoreit.mappers.ProductMapper;
import com.restoreit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO GetProductByID(UUID id){
        return productRepository.findById(id)
                .map(productMapper::ProductToDTO)
                .orElse(null);
    }

    public boolean CreateProduct(ProductDTO productDto){
        return productRepository.save( productMapper.DTOToProduct(productDto)) != null;
    }

    public List<ProductDTO> GetUserProducts(UUID id){
        return productRepository.findByUserId(id)
                .stream()
                .map(productMapper::ProductToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO GetProductByUserID(UUID productId, UUID userId){
        return productMapper.ProductToDTO(productRepository.findByIdAndUserId(productId, userId));
    }

    public List<ProductDTO> GetAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(productMapper::ProductToDTO)
                .collect(Collectors.toList());
    }

    public void DeleteProduct(UUID id){
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }//Only ID exists, if not throw exception
    }

    public void EditProduct(ProductDTO productDto){
        productRepository.save(productMapper.DTOToProduct(productDto));
        //will this duplicate my product?
    }
}
