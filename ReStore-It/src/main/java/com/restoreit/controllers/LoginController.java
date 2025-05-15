package com.restoreit.controllers;

import com.restoreit.dtos.UserDTO;
import com.restoreit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/validate")
    public ResponseEntity<UserDTO> ValidateLoginCredentials(@RequestParam String email, @RequestParam String password){
        UserDTO user = userService.GetUserByEmail(email);

        //Research more secure validation...next security research report
        // see how to hash/encrypt/encode the password during data transfer
        if(user != null && user.email.equals(email) && user.password.equals(password)){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
