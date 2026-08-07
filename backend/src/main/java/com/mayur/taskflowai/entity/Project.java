package com.mayur.taskflowai.entity;

import com.mayur.taskflowai.enums.ProjectStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_code", nullable = false, unique = true)
    private String projectCode;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(length = 500)
    private String description;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "required_experience", nullable = false)
    private Integer requiredExperience;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @ManyToMany
    @JoinTable(
            name = "project_skill",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> requiredSkills = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Project() {
    }

    public Project(Long id,
                   String projectCode,
                   String projectName,
                   String description,
                   String clientName,
                   LocalDate startDate,
                   LocalDate endDate,
                   Integer requiredExperience,
                   ProjectStatus status,
                   Set<Skill> requiredSkills,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {

        this.id = id;
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.description = description;
        this.clientName = clientName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requiredExperience = requiredExperience;
        this.status = status;
        this.requiredSkills = requiredSkills;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public Set<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(Set<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}