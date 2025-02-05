package com.app.app.Controller;

import com.app.app.Models.UserDetails;
import com.app.app.Services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        UserDetails user = userDetailsService.getUserByEmail(userDetails.getEmail());
        if (user != null && user.getPassword().equals(userDetails.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }


    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> register(@RequestBody UserDetails userDetails) {
        try {
            if (userDetails.getEmail() == null || userDetails.getPassword() == null) {
                return ResponseEntity.status(400).body(Map.of("error", "Email and password are required"));
            }

            if (userDetailsService.getUserByEmail(userDetails.getEmail()) != null) {
                return ResponseEntity.status(400).body(Map.of("error", "Email already registered"));
            }

            UserDetails newUser = userDetailsService.createUser(userDetails);
            return ResponseEntity.status(201).body(Map.of("message", "User registered successfully", "userId", newUser.getUserId()));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
