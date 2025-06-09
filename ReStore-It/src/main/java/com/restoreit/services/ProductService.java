package com.restoreit.services;

import com.restoreit.dtos.ProductDTO;
import com.restoreit.mappers.ProductMapper;
import com.restoreit.mappers.UserMapper;
import com.restoreit.models.Product;
import com.restoreit.models.User;
import com.restoreit.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper, UserService userService, UserMapper userMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public ProductDTO GetProductByID(UUID id){
        return productRepository.findById(id)
                .map(productMapper::ProductToDTO)
                .orElse(null);
    }

    public boolean CreateProduct(ProductDTO productDto, UUID userId) {
        Product product = productMapper.DTOToProduct(productDto);
        User user = userMapper.DTOToUser(userService.GetUserByID(userId));
        if(user!=null){
            product.setUser(user);
            product.setSeller(user.getName());
            return productRepository.save(product) != null;
        }
        return false;
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
        } else {
            throw new EntityNotFoundException("Product with ID " + id + " not found.");
        }
    }

    public void EditProduct(ProductDTO productDto) {
        Optional<Product> existingProduct = productRepository.findById(productDto.id);

        if (existingProduct.isPresent()) {
            Product updatedProduct = productMapper.DTOToProduct(productDto);
            updatedProduct.setUser(existingProduct.get().getUser());

            productRepository.save(updatedProduct);
        } else {
            throw new EntityNotFoundException("Product with ID :" + productDto.id + " not found.");
        }
    }
}
