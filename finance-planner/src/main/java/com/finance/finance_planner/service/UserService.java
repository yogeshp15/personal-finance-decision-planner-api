package com.finance.finance_planner.service;

import com.finance.finance_planner.dto.LoginRequestDTO;
import com.finance.finance_planner.dto.LoginResponseDTO;
import com.finance.finance_planner.dto.RegisterRequestDTO;
import com.finance.finance_planner.dto.RegisterResponseDTO;
import com.finance.finance_planner.entity.UserEntity;
import com.finance.finance_planner.exception.EmailAlreadyExistsException;
import com.finance.finance_planner.exception.InvalidEmailAndPassException;
import com.finance.finance_planner.repository.UserRepository;
import com.finance.finance_planner.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    public RegisterResponseDTO registerUser(RegisterRequestDTO request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email Already Exists");
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        user.setName(request.getName());
        UserEntity savedUser = userRepository.save(user);

        return new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public LoginResponseDTO checkLogin(LoginRequestDTO request){
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new InvalidEmailAndPassException(("Invalid email or password")));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new InvalidEmailAndPassException("Invalid email or password");
        }
        String token = jwtService.generateToken(request.getEmail());
        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token
        );
    }
}
