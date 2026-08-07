package com.mayur.taskflowai.mapper;

import com.mayur.taskflowai.dto.request.EmployeeRequestDTO;
import com.mayur.taskflowai.dto.response.EmployeeResponseDTO;
import com.mayur.taskflowai.dto.response.SkillResponseDTO;
import com.mayur.taskflowai.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeMapper {

    private final SkillMapper skillMapper;

    public EmployeeMapper(SkillMapper skillMapper) {
        this.skillMapper = skillMapper;
    }

    public Employee toEntity(EmployeeRequestDTO dto) {

        Employee employee = new Employee();

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setDepartment(dto.getDepartment());
        employee.setDesignation(dto.getDesignation());
        employee.setExperience(dto.getExperience());
        employee.setAvailability(dto.getAvailability());

        return employee;
    }

    public EmployeeResponseDTO toResponse(Employee employee) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(employee.getId());
        dto.setEmployeeCode(employee.getEmployeeCode());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setDesignation(employee.getDesignation());
        dto.setExperience(employee.getExperience());
        dto.setAvailability(employee.getAvailability());

        List<SkillResponseDTO> skills = employee.getSkills()
                .stream()
                .map(skillMapper::toResponse)
                .toList();

        dto.setSkills(skills);

        return dto;
    }
}