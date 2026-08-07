package com.mayur.taskflowai.mapper;

import com.mayur.taskflowai.dto.request.ProjectRequestDTO;
import com.mayur.taskflowai.dto.response.ProjectResponseDTO;
import com.mayur.taskflowai.dto.response.SkillResponseDTO;
import com.mayur.taskflowai.entity.Project;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMapper {

    private final SkillMapper skillMapper;

    public ProjectMapper(SkillMapper skillMapper) {
        this.skillMapper = skillMapper;
    }

    public Project toEntity(ProjectRequestDTO requestDTO) {

        Project project = new Project();

        project.setProjectCode(requestDTO.getProjectCode());
        project.setProjectName(requestDTO.getProjectName());
        project.setDescription(requestDTO.getDescription());
        project.setClientName(requestDTO.getClientName());
        project.setStartDate(requestDTO.getStartDate());
        project.setEndDate(requestDTO.getEndDate());
        project.setRequiredExperience(requestDTO.getRequiredExperience());
        project.setStatus(requestDTO.getStatus());

        return project;
    }

    public ProjectResponseDTO toResponse(Project project) {

        ProjectResponseDTO responseDTO = new ProjectResponseDTO();

        responseDTO.setId(project.getId());
        responseDTO.setProjectCode(project.getProjectCode());
        responseDTO.setProjectName(project.getProjectName());
        responseDTO.setDescription(project.getDescription());
        responseDTO.setClientName(project.getClientName());
        responseDTO.setStartDate(project.getStartDate());
        responseDTO.setEndDate(project.getEndDate());
        responseDTO.setRequiredExperience(project.getRequiredExperience());
        responseDTO.setStatus(project.getStatus());

        List<SkillResponseDTO> skills =
                project.getRequiredSkills()
                        .stream()
                        .map(skillMapper::toResponse)
                        .toList();

        responseDTO.setRequiredSkills(skills);

        return responseDTO;
    }
}