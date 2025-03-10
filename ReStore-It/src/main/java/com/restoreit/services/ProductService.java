package com.restoreit.services;

import com.restoreit.models.Product;
import com.restoreit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product GetProductByID(Integer id){
        return productRepository.findById(id).get();
    }

    public boolean CreateProduct(Product product){
        return productRepository.save(product) != null;
    }

    public List<Product> GetAllProducts(){
        return productRepository.findAll();
    }

    public void DeleteProduct(Integer id){
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }
    }

    public void UpdateProduct(Product product){
        productRepository.save(product);
    }
}
