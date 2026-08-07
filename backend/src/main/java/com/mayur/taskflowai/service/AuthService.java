package com.mayur.taskflowai.service;

import com.mayur.taskflowai.dto.request.LoginRequestDTO;
import com.mayur.taskflowai.dto.response.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO requestDTO);

}