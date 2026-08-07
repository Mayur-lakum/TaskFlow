package com.mayur.taskflowai.controller;

import com.mayur.taskflowai.dto.request.ProjectRequestDTO;
import com.mayur.taskflowai.dto.response.ProjectResponseDTO;
import com.mayur.taskflowai.response.ApiResponse;
import com.mayur.taskflowai.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/projects")
@Tag(
        name = "Project Management",
        description = "APIs for managing projects"
)
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> createProject(
            @Valid @RequestBody ProjectRequestDTO requestDTO) {

        ProjectResponseDTO project = projectService.saveProject(requestDTO);

        ApiResponse<ProjectResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Project created successfully",
                        project,
                        LocalDateTime.now()
                );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponseDTO>>> getAllProjects() {

        List<ProjectResponseDTO> projects = projectService.getAllProjects();

        ApiResponse<List<ProjectResponseDTO>> response =
                new ApiResponse<>(
                        true,
                        "Projects fetched successfully",
                        projects,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> getProjectById(
            @PathVariable Long id) {

        ProjectResponseDTO project = projectService.getProjectById(id);

        ApiResponse<ProjectResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Project fetched successfully",
                        project,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequestDTO requestDTO) {

        ProjectResponseDTO project =
                projectService.updateProject(id, requestDTO);

        ApiResponse<ProjectResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Project updated successfully",
                        project,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
            @PathVariable Long id) {

        projectService.deleteProject(id);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        "Project deleted successfully",
                        null,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }
}