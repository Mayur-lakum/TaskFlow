package com.mayur.taskflowai.service.impl;

import com.mayur.taskflowai.dto.request.SkillRequestDTO;
import com.mayur.taskflowai.dto.response.SkillResponseDTO;
import com.mayur.taskflowai.entity.Skill;
import com.mayur.taskflowai.exception.ResourceNotFoundException;
import com.mayur.taskflowai.mapper.SkillMapper;
import com.mayur.taskflowai.repository.SkillRepository;
import com.mayur.taskflowai.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository,
                            SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public SkillResponseDTO saveSkill(SkillRequestDTO requestDTO) {

        if (skillRepository.existsBySkillName(requestDTO.getSkillName())) {
            throw new IllegalArgumentException(
                    "Skill already exists: " + requestDTO.getSkillName()
            );
        }

        Skill skill = skillMapper.toEntity(requestDTO);

        Skill savedSkill = skillRepository.save(skill);

        return skillMapper.toResponse(savedSkill);
    }

    @Override
    public List<SkillResponseDTO> getAllSkills() {

        List<Skill> skills = skillRepository.findAll();

        return skills.stream()
                .map(skillMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SkillResponseDTO getSkillById(Long id) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with id: " + id));

        return skillMapper.toResponse(skill);
    }

    @Override
    public SkillResponseDTO updateSkill(Long id,
                                        SkillRequestDTO requestDTO) {

        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with id: " + id));

        existingSkill.setSkillName(requestDTO.getSkillName());
        existingSkill.setDescription(requestDTO.getDescription());

        Skill updatedSkill = skillRepository.save(existingSkill);

        return skillMapper.toResponse(updatedSkill);
    }

    @Override
    public void deleteSkill(Long id) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with id: " + id));

        skillRepository.delete(skill);
    }
}