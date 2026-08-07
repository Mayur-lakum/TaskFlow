package com.mayur.taskflowai.service;

import com.mayur.taskflowai.dto.request.SkillRequestDTO;
import com.mayur.taskflowai.dto.response.SkillResponseDTO;

import java.util.List;

public interface SkillService {

    SkillResponseDTO saveSkill(SkillRequestDTO requestDTO);

    List<SkillResponseDTO> getAllSkills();

    SkillResponseDTO getSkillById(Long id);

    SkillResponseDTO updateSkill(Long id,
                                 SkillRequestDTO requestDTO);

    void deleteSkill(Long id);

}