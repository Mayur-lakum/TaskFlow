package com.mayur.taskflowai.dto.response;

public class SkillResponseDTO {

    private Long id;

    private String skillName;

    private String description;

    public SkillResponseDTO() {
    }

    public SkillResponseDTO(Long id,
                            String skillName,
                            String description) {
        this.id = id;
        this.skillName = skillName;
        this.description = description;
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
}