package com.restoreit.controllers;

import com.restoreit.dtos.ProductDTO;
import com.restoreit.services.JWTService;
import com.restoreit.services.ProductService;
import com.restoreit.services.UserService;
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

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;

    //Guest Mapping: No authorization needed
    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> GetAllProducts(){
        return ResponseEntity.ok(productService.GetAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> GetProductByID(@PathVariable UUID id){
          return ResponseEntity.ok(productService.GetProductByID(id));
    }

    //Business Mapping
    @GetMapping("/business")
    public ResponseEntity<List<ProductDTO>> GetUserProducts(@RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            if(jwtService.validateToken(token, extractedUserId)){
                UUID userId = UUID.fromString(extractedUserId);
                return ResponseEntity.ok(productService.GetUserProducts(userId));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/business/{productId}")
    public ResponseEntity<ProductDTO> GetProductByUserID(@PathVariable UUID productId, @RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            if(jwtService.validateToken(token, extractedUserId)) {
                UUID userId = UUID.fromString(extractedUserId);
                return ResponseEntity.ok(productService.GetProductByUserID(productId, userId));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/business/create")
    public ResponseEntity<Boolean> CreateProduct(@RequestBody ProductDTO product, @RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            if(jwtService.validateToken(token, extractedUserId)) {
                UUID userId = UUID.fromString(extractedUserId);

                if(productService.CreateProduct(product, userId)){
                return ResponseEntity.status(HttpStatus.CREATED).body(true);
                }
            }
        }
        return ResponseEntity.badRequest().body(false);
    }

    @DeleteMapping("/business/delete/{id}")
    public ResponseEntity<Void>DeleteProduct(@PathVariable UUID id , @RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            if(jwtService.validateToken(token, extractedUserId)) {
                productService.DeleteProduct(id);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/business/edit")
    public ResponseEntity<Boolean> EditProduct(@RequestBody ProductDTO product, @RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            if(jwtService.validateToken(token, extractedUserId)) {
                productService.EditProduct(product);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
