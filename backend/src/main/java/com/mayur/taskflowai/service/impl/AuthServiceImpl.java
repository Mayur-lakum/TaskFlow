package com.mayur.taskflowai.service.impl;

import com.mayur.taskflowai.dto.request.LoginRequestDTO;
import com.mayur.taskflowai.dto.response.LoginResponseDTO;
import com.mayur.taskflowai.entity.User;
import com.mayur.taskflowai.repository.UserRepository;
import com.mayur.taskflowai.security.JwtUtil;
import com.mayur.taskflowai.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil,
                           UserRepository userRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {

        // Authenticate username and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUsername(),
                        requestDTO.getPassword()
                )
        );

        // Generate JWT
        String token = jwtUtil.generateToken(requestDTO.getUsername());

        // Fetch user to get role
        User user = userRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Return response
        return new LoginResponseDTO(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }
}