package com.mayur.taskflowai.dto.response;

import com.mayur.taskflowai.enums.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public class ProjectResponseDTO {

    private Long id;

    private String projectCode;

    private String projectName;

    private String description;

    private String clientName;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer requiredExperience;

    private ProjectStatus status;

    // NEW
    private List<SkillResponseDTO> requiredSkills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getRequiredExperience() {
        return requiredExperience;
    }

    public void setRequiredExperience(Integer requiredExperience) {
        this.requiredExperience = requiredExperience;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public List<SkillResponseDTO> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<SkillResponseDTO> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
}