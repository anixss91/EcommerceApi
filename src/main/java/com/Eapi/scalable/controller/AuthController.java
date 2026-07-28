package com.Eapi.scalable.controller;

import com.Eapi.scalable.model.LoginRequest;
import com.Eapi.scalable.model.RegisterRequest;
import com.Eapi.scalable.model.User;
import com.Eapi.scalable.repository.UserRepo;
import com.Eapi.scalable.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepo.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setRole("CUSTOMER");
        userRepo.save(user);

        return ResponseEntity.ok().body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return userRepo.findByEmail(loginRequest.getEmail())
                .map(user -> {
                    if(passwordEncoder.matches(loginRequest.getPassword() , user.getPassword())){
                        String token = jwtUtil.generateToken(user.getEmail());
                        return ResponseEntity.ok(token);
                    }
                    return ResponseEntity.badRequest().body("Invalid username or password");
                })
                .orElse(ResponseEntity.badRequest().body("email not found"));
    }

}
