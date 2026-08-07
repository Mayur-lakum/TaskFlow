package com.mayur.taskflowai.controller;

import com.mayur.taskflowai.dto.request.SkillRequestDTO;
import com.mayur.taskflowai.dto.response.SkillResponseDTO;
import com.mayur.taskflowai.response.ApiResponse;
import com.mayur.taskflowai.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/skills")
@Tag(
        name = "Skill Management",
        description = "APIs for managing skills"
)
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SkillResponseDTO>> createSkill(
            @Valid @RequestBody SkillRequestDTO requestDTO) {

        SkillResponseDTO responseDTO = skillService.saveSkill(requestDTO);

        ApiResponse<SkillResponseDTO> response = new ApiResponse<>(
                true,
                "Skill created successfully",
                responseDTO,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponseDTO>>> getAllSkills() {

        List<SkillResponseDTO> skills = skillService.getAllSkills();

        ApiResponse<List<SkillResponseDTO>> response = new ApiResponse<>(
                true,
                "Skills fetched successfully",
                skills,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponseDTO>> getSkillById(
            @PathVariable Long id) {

        SkillResponseDTO responseDTO = skillService.getSkillById(id);

        ApiResponse<SkillResponseDTO> response = new ApiResponse<>(
                true,
                "Skill fetched successfully",
                responseDTO,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponseDTO>> updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody SkillRequestDTO requestDTO) {

        SkillResponseDTO responseDTO =
                skillService.updateSkill(id, requestDTO);

        ApiResponse<SkillResponseDTO> response = new ApiResponse<>(
                true,
                "Skill updated successfully",
                responseDTO,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(
            @PathVariable Long id) {

        skillService.deleteSkill(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Skill deleted successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}