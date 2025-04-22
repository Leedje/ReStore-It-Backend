package com.restoreit.controllers;

import com.restoreit.dtos.ProductDTO;
import com.restoreit.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<ProductDTO> GetAllProducts(){
        return productService.GetAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO GetProductByID(@PathVariable Integer id){
        return productService.GetProductByID(id);
    }

    @PostMapping("/create")
    public boolean CreateProduct(@RequestBody ProductDTO product){
        return productService.CreateProduct(product);
    }

    @DeleteMapping("/delete/{id}") //See if it's possible to delete without using the URL.
    public ResponseEntity<Void>DeleteProduct(@PathVariable Integer id){
        productService.DeleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
