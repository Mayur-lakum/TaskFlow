package com.mayur.taskflowai.controller;

import com.mayur.taskflowai.dto.response.RecommendationResponseDTO;
import com.mayur.taskflowai.response.ApiResponse;
import com.mayur.taskflowai.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/recommendations")
@Tag(
        name = "Recommendation Engine",
        description = "AI-based employee recommendation APIs"
)
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService) {

        this.recommendationService = recommendationService;
    }

    @GetMapping("/{projectId}")
    public ApiResponse<List<RecommendationResponseDTO>>
    recommendEmployees(@PathVariable Long projectId) {

        List<RecommendationResponseDTO> recommendations =
                recommendationService.recommendEmployees(projectId);

        return new ApiResponse<>(

                true,

                "Recommended employees fetched successfully",

                recommendations,

                LocalDateTime.now()
        );
    }
}