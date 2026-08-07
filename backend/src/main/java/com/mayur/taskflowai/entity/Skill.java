package com.mayur.taskflowai.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_name", nullable = false, unique = true)
    private String skillName;

    @Column(length = 300)
    private String description;

    @ManyToMany(mappedBy = "skills")
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany(mappedBy = "requiredSkills")
    private Set<Project> projects = new HashSet<>();

    public Skill() {
    }

    public Skill(Long id,
                 String skillName,
                 String description,
                 Set<Employee> employees,
                 Set<Project> projects) {
        this.id = id;
        this.skillName = skillName;
        this.description = description;
        this.employees = employees;
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}