package com.restoreit.controllers;

import com.restoreit.dtos.OrderDTO;
import com.restoreit.services.JWTService;
import com.restoreit.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JWTService jwtService;

    //Guest mapping
    @PostMapping("/submit")
    public ResponseEntity<UUID> SubmitOrder(@RequestBody OrderDTO order) {
        UUID orderID = orderService.CreateOrder(order);
        if (orderID != null
                && !orderID.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderID);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> GetOrderById(@PathVariable UUID id){
        return ResponseEntity.ok(orderService.GetOrderById(id));
    }

    //Business mapping
    @GetMapping("/pending")
    public ResponseEntity<List<OrderDTO>> GetPendingOrdersByUserId(@RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            if(jwtService.validateToken(token, extractedUserId)){
                UUID userId = UUID.fromString(extractedUserId);
                return ResponseEntity.ok(orderService.GetPendingOrdersByUserId(userId));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/completed")
    public ResponseEntity<List<OrderDTO>> GetCompletedOrdersByUserId(@RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            if(jwtService.validateToken(token, extractedUserId)){
                UUID userId = UUID.fromString(extractedUserId);
                return ResponseEntity.ok(orderService.GetCompletedOrdersByUserId(userId));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/set-complete")
    public ResponseEntity<Boolean> SetOrderAsComplete(@RequestBody OrderDTO order, @RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            System.out.println("Received orderId: " + order.id);

            if(jwtService.validateToken(token, extractedUserId)){
                UUID userId = UUID.fromString(extractedUserId);
                return ResponseEntity.ok(orderService.SetOrderAsCompleted(order, userId));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
