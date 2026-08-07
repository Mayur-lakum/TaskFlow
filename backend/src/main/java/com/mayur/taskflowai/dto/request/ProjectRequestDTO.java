package com.mayur.taskflowai.dto.request;

import com.mayur.taskflowai.enums.ProjectStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public class ProjectRequestDTO {

    @NotBlank(message = "Project code is required")
    private String projectCode;

    @NotBlank(message = "Project name is required")
    private String projectName;

    private String description;

    @NotBlank(message = "Client name is required")
    private String clientName;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Required experience is required")
    @Min(value = 0, message = "Required experience cannot be negative")
    private Integer requiredExperience;

    @NotNull(message = "Project status is required")
    private ProjectStatus status;

    // NEW: Required Skills
    private Set<Long> skillIds;

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

    public Set<Long> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(Set<Long> skillIds) {
        this.skillIds = skillIds;
    }
}