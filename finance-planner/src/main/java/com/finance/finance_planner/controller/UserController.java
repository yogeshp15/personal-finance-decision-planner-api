package com.finance.finance_planner.controller;

import com.finance.finance_planner.dto.LoginRequestDTO;
import com.finance.finance_planner.dto.LoginResponseDTO;
import com.finance.finance_planner.dto.RegisterRequestDTO;
import com.finance.finance_planner.dto.RegisterResponseDTO;
import com.finance.finance_planner.entity.UserEntity;
import com.finance.finance_planner.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request){
        RegisterResponseDTO user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request){
        LoginResponseDTO user = userService.checkLogin(request);
        return ResponseEntity.ok(user);
    }
}
