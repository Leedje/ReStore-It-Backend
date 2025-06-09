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
@RequestMapping("/order")
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

    @DeleteMapping("/cancel/{orderId}") //this function should be on the error page
    //Unsure or made a mistake? button: Cancel order
    public ResponseEntity<Boolean> CancelOrder(@PathVariable UUID orderId){
        //inside the service, I need to make sure that all chats are deleted from that order as well
        //maybe I can turn that into an async live update as soon as it gets cancelled?
        //ONLY IFF !! I have time left fr fr
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> GetOrderById(@PathVariable UUID orderId){
        return ResponseEntity.ok(orderService.GetOrderById(orderId));
    }

    //Business mapping
    @GetMapping("/business/pending")
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

    @GetMapping("/business/completed")
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

    @PostMapping("/business/iscomplete")
    public ResponseEntity<Boolean> SetOrderAsComplete(@RequestBody OrderDTO order, @RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            if(jwtService.validateToken(token, extractedUserId)){
                UUID userId = UUID.fromString(extractedUserId);
                return ResponseEntity.ok(orderService.SetOrderAsCompleted(order, userId));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
