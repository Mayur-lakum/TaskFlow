package com.mayur.taskflowai.mapper;

import com.mayur.taskflowai.dto.request.SkillRequestDTO;
import com.mayur.taskflowai.dto.response.SkillResponseDTO;
import com.mayur.taskflowai.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public Skill toEntity(SkillRequestDTO requestDTO) {

        Skill skill = new Skill();

        skill.setSkillName(requestDTO.getSkillName());
        skill.setDescription(requestDTO.getDescription());

        return skill;
    }

    public SkillResponseDTO toResponse(Skill skill) {

        SkillResponseDTO responseDTO = new SkillResponseDTO();

        responseDTO.setId(skill.getId());
        responseDTO.setSkillName(skill.getSkillName());
        responseDTO.setDescription(skill.getDescription());

        return responseDTO;
    }

}