package com.mayur.taskflowai.dto.request;

import jakarta.validation.constraints.NotBlank;

public class SkillRequestDTO {

    @NotBlank(message = "Skill name is required")
    private String skillName;

    private String description;

    public SkillRequestDTO() {
    }

    public SkillRequestDTO(String skillName, String description) {
        this.skillName = skillName;
        this.description = description;
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
}