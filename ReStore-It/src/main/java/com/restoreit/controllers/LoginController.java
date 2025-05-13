package com.restoreit.controllers;

import com.restoreit.dtos.UserDTO;
import com.restoreit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> ValidateLoginCredentials(@RequestParam String email, @RequestParam String password){
        UserDTO user = userService.GetUserByEmail(email);

        //Research more secure validation...next security research report
        if(user != null && user.email.equals(email) && user.password.equals(password)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
}
