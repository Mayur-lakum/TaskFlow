package com.mayur.taskflowai.service;

import com.mayur.taskflowai.dto.request.ProjectRequestDTO;
import com.mayur.taskflowai.dto.response.ProjectResponseDTO;

import java.util.List;

public interface ProjectService {

    ProjectResponseDTO saveProject(ProjectRequestDTO requestDTO);

    List<ProjectResponseDTO> getAllProjects();

    ProjectResponseDTO getProjectById(Long id);

    ProjectResponseDTO updateProject(Long id,
                                     ProjectRequestDTO requestDTO);

    void deleteProject(Long id);

}