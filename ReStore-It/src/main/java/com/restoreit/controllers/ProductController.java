package com.restoreit.controllers;

import com.restoreit.models.Product;
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
    public List<Product> GetAllProducts(){
        return productService.GetAllProducts();
    }

    @GetMapping("/{id}")
    public Product GetProductByID(@PathVariable Integer id){
        return productService.GetProductByID(id);
    }

    //Path variable annotation for an object/model?
    @PostMapping("/create")
    public boolean CreateProduct(@RequestBody Product product){
        return productService.CreateProduct(product);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>DeleteProduct(@PathVariable Integer id){
        productService.DeleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
