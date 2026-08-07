package com.mayur.taskflowai.controller;

import com.mayur.taskflowai.dto.request.LoginRequestDTO;
import com.mayur.taskflowai.dto.response.LoginResponseDTO;
import com.mayur.taskflowai.response.ApiResponse;
import com.mayur.taskflowai.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Authentication",
        description = "JWT authentication APIs"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO requestDTO) {

        LoginResponseDTO responseDTO = authService.login(requestDTO);

        ApiResponse<LoginResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Login Successful",
                        responseDTO,
                        LocalDateTime.now()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }
}