package com.restoreit.controllers;

import com.restoreit.dtos.ProductDTO;
import com.restoreit.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //Guest Mapping
    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> GetAllProducts(){
        return ResponseEntity.ok(productService.GetAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> GetProductByID(@PathVariable UUID id){
        return ResponseEntity.ok(productService.GetProductByID(id));
    }

    //Business Mapping
    @GetMapping("/business/{userId}")
    public ResponseEntity<List<ProductDTO>> GetUserProducts(@PathVariable UUID userId){
        return ResponseEntity.ok(productService.GetUserProducts(userId));
    }

    @GetMapping("/business/{productId}/{userId}")
    public ResponseEntity<ProductDTO> GetProductByUserID(@PathVariable UUID productId, @PathVariable UUID userId){
        return ResponseEntity.ok(productService.GetProductByUserID(productId, userId));
    }

    @PostMapping("/business/create")
    public ResponseEntity<Boolean> CreateProduct(@RequestBody ProductDTO product){
        if(productService.CreateProduct(product)){
                return ResponseEntity.status(HttpStatus.CREATED).body(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @DeleteMapping("/business/delete/{id}")
    public ResponseEntity<Void>DeleteProduct(@PathVariable UUID id){
        productService.DeleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
