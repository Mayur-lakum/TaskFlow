package com.mayur.taskflowai.service.impl;

import com.mayur.taskflowai.dto.request.ProjectRequestDTO;
import com.mayur.taskflowai.dto.response.ProjectResponseDTO;
import com.mayur.taskflowai.entity.Project;
import com.mayur.taskflowai.entity.Skill;
import com.mayur.taskflowai.exception.ResourceNotFoundException;
import com.mayur.taskflowai.mapper.ProjectMapper;
import com.mayur.taskflowai.repository.ProjectRepository;
import com.mayur.taskflowai.repository.SkillRepository;
import com.mayur.taskflowai.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final SkillRepository skillRepository;

    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              SkillRepository skillRepository,
                              ProjectMapper projectMapper) {

        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectResponseDTO saveProject(ProjectRequestDTO requestDTO) {

        if (projectRepository.existsByProjectCode(requestDTO.getProjectCode())) {

            throw new IllegalArgumentException(
                    "Project code already exists : "
                            + requestDTO.getProjectCode()
            );
        }

        Project project = projectMapper.toEntity(requestDTO);

        Set<Skill> skills = new HashSet<>();

        if (requestDTO.getSkillIds() != null) {

            skills = requestDTO.getSkillIds()
                    .stream()
                    .map(id -> skillRepository.findById(id)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Skill not found with id : " + id)))
                    .collect(Collectors.toSet());
        }

        project.setRequiredSkills(skills);

        Project savedProject =
                projectRepository.save(project);

        return projectMapper.toResponse(savedProject);
    }

    @Override
    public List<ProjectResponseDTO> getAllProjects() {

        List<Project> projects =
                projectRepository.findAll();

        return projects.stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDTO getProjectById(Long id) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found with id : "
                                                + id));

        return projectMapper.toResponse(project);
    }    @Override
    public ProjectResponseDTO updateProject(Long id,
                                            ProjectRequestDTO requestDTO) {

        Project existingProject =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found with id : "
                                                + id));

        existingProject.setProjectCode(requestDTO.getProjectCode());
        existingProject.setProjectName(requestDTO.getProjectName());
        existingProject.setDescription(requestDTO.getDescription());
        existingProject.setClientName(requestDTO.getClientName());
        existingProject.setStartDate(requestDTO.getStartDate());
        existingProject.setEndDate(requestDTO.getEndDate());
        existingProject.setRequiredExperience(requestDTO.getRequiredExperience());
        existingProject.setStatus(requestDTO.getStatus());

        Set<Skill> skills = new HashSet<>();

        if (requestDTO.getSkillIds() != null) {

            skills = requestDTO.getSkillIds()
                    .stream()
                    .map(skillId -> skillRepository.findById(skillId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Skill not found with id : "
                                                    + skillId)))
                    .collect(Collectors.toSet());
        }

        existingProject.setRequiredSkills(skills);

        Project updatedProject =
                projectRepository.save(existingProject);

        return projectMapper.toResponse(updatedProject);
    }

    @Override
    public void deleteProject(Long id) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found with id : "
                                                + id));

        projectRepository.delete(project);
    }
}