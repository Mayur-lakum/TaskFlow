package com.mayur.taskflowai.service;

import com.mayur.taskflowai.dto.response.RecommendationResponseDTO;

import java.util.List;

public interface RecommendationService {

    List<RecommendationResponseDTO> recommendEmployees(Long projectId);

}