package com.restoreit.controllers;

import com.restoreit.services.UserService;
import com.restoreit.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/create")
    public ResponseEntity<Boolean> CreateUser(@RequestBody UserDTO user){
        return ResponseEntity.ok(userService.CreateUser(user));
    }

}
