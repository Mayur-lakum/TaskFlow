package com.mayur.taskflowai.service.impl;

import com.mayur.taskflowai.dto.response.RecommendationResponseDTO;
import com.mayur.taskflowai.entity.Employee;
import com.mayur.taskflowai.entity.Project;
import com.mayur.taskflowai.exception.ResourceNotFoundException;
import com.mayur.taskflowai.repository.EmployeeRepository;
import com.mayur.taskflowai.repository.ProjectRepository;
import com.mayur.taskflowai.service.RecommendationService;
import com.mayur.taskflowai.util.RecommendationEngine;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public RecommendationServiceImpl(EmployeeRepository employeeRepository,
                                     ProjectRepository projectRepository) {

        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<RecommendationResponseDTO> recommendEmployees(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id : " + projectId));

        return employeeRepository.findAll()

                .stream()

                .filter(employee ->
                        Boolean.TRUE.equals(employee.getAvailability()))

                .map(employee -> {

                    long matchedSkills = employee.getSkills()
                            .stream()
                            .filter(project.getRequiredSkills()::contains)
                            .count();

                    int score =
                            RecommendationEngine.calculateScore(employee, project);

                    return new RecommendationResponseDTO(

                            employee.getId(),

                            employee.getFirstName() + " " + employee.getLastName(),

                            employee.getEmployeeCode(),

                            employee.getDepartment(),

                            (int) matchedSkills,

                            employee.getExperience(),

                            score
                    );
                })
                .sorted(
                        Comparator.comparingInt(
                                        RecommendationResponseDTO::getScore)
                                .reversed())

                .limit(5)

                .collect(Collectors.toList());
    }
}