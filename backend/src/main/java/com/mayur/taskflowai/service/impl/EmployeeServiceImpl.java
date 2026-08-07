package com.mayur.taskflowai.service.impl;

import com.mayur.taskflowai.dto.request.EmployeeRequestDTO;
import com.mayur.taskflowai.dto.response.EmployeeResponseDTO;
import com.mayur.taskflowai.dto.response.SkillResponseDTO;
import com.mayur.taskflowai.entity.Employee;
import com.mayur.taskflowai.entity.Skill;
import com.mayur.taskflowai.exception.ResourceNotFoundException;
import com.mayur.taskflowai.mapper.EmployeeMapper;
import com.mayur.taskflowai.mapper.SkillMapper;
import com.mayur.taskflowai.repository.EmployeeRepository;
import com.mayur.taskflowai.repository.SkillRepository;
import com.mayur.taskflowai.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeMapper employeeMapper,
                               SkillRepository skillRepository,
                               SkillMapper skillMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDTO) {

        Employee employee = employeeMapper.toEntity(requestDTO);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDTO> searchEmployees(String keyword) {

        return employeeRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCaseOrDesignationContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword,
                        keyword,
                        keyword
                )
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponseDTO> filterEmployees(
            String department,
            Boolean availability) {

        List<Employee> employees;

        if (department != null && availability != null) {

            employees = employeeRepository
                    .findByDepartmentIgnoreCaseAndAvailability(
                            department,
                            availability
                    );

        } else if (department != null) {

            employees = employeeRepository
                    .findByDepartmentIgnoreCase(department);

        } else if (availability != null) {

            employees = employeeRepository
                    .findByAvailability(availability);

        } else {

            employees = Collections.emptyList();
        }

        return employees.stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    public Page<EmployeeResponseDTO> getEmployees(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage =
                employeeRepository.findAll(pageable);

        return employeePage.map(employeeMapper::toResponse);
    }


    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id,
                                              EmployeeRequestDTO requestDTO) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        employee.setFirstName(requestDTO.getFirstName());
        employee.setLastName(requestDTO.getLastName());
        employee.setEmail(requestDTO.getEmail());
        employee.setPhone(requestDTO.getPhone());
        employee.setDepartment(requestDTO.getDepartment());
        employee.setDesignation(requestDTO.getDesignation());
        employee.setExperience(requestDTO.getExperience());
        employee.setAvailability(requestDTO.getAvailability());

        Employee updatedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        employeeRepository.delete(employee);
    }

    @Override
    public void assignSkillToEmployee(Long employeeId,
                                      Long skillId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + employeeId));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with id: " + skillId));

        employee.getSkills().add(skill);

        employeeRepository.save(employee);
    }

    @Override
    public void removeSkillFromEmployee(Long employeeId,
                                        Long skillId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + employeeId));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with id: " + skillId));

        employee.getSkills().remove(skill);

        employeeRepository.save(employee);
    }

    @Override
    public List<SkillResponseDTO> getEmployeeSkills(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + employeeId));

        return employee.getSkills()
                .stream()
                .map(skillMapper::toResponse)
                .toList();
    }
}