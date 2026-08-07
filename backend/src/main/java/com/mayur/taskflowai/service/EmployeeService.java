package com.mayur.taskflowai.service;

import com.mayur.taskflowai.dto.request.EmployeeRequestDTO;
import com.mayur.taskflowai.dto.response.EmployeeResponseDTO;
import com.mayur.taskflowai.dto.response.SkillResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDTO);

    List<EmployeeResponseDTO> getAllEmployees();

    List<EmployeeResponseDTO> searchEmployees(String keyword);

    List<EmployeeResponseDTO> filterEmployees(
            String department,
            Boolean availability
    );

    Page<EmployeeResponseDTO> getEmployees(
            int page,
            int size,
            String sortBy,
            String direction
    );

    EmployeeResponseDTO getEmployeeById(Long id);

    EmployeeResponseDTO updateEmployee(Long id,
                                       EmployeeRequestDTO requestDTO);

    void deleteEmployee(Long id);

    void assignSkillToEmployee(Long employeeId,
                               Long skillId);

    void removeSkillFromEmployee(Long employeeId,
                                 Long skillId);

    List<SkillResponseDTO> getEmployeeSkills(Long employeeId);
}