package com.restoreit.controllers;

import com.restoreit.dtos.LoginDTO;
import com.restoreit.dtos.UserDTO;
import com.restoreit.services.JWTService;
import com.restoreit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/validate")
    public ResponseEntity<?> ValidateLoginCredentials(@RequestBody LoginDTO loginCredentials){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(14);
        UserDTO user = userService.GetUserByEmail(loginCredentials.getEmail());

        if(user.email.equals(loginCredentials.getEmail()) && encoder.matches(loginCredentials.getPassword(), user.password)){
            String token = jwtService.generateToken(user);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
