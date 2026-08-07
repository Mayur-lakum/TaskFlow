package com.mayur.taskflowai.controller;

import com.mayur.taskflowai.dto.request.EmployeeRequestDTO;
import com.mayur.taskflowai.dto.response.EmployeeResponseDTO;
import com.mayur.taskflowai.dto.response.SkillResponseDTO;
import com.mayur.taskflowai.response.ApiResponse;
import com.mayur.taskflowai.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(
        name = "Employee Management",
        description = "APIs for managing employees"
)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> saveEmployee(
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        EmployeeResponseDTO employee =
                employeeService.saveEmployee(requestDTO);

        ApiResponse<EmployeeResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Employee created successfully",
                        employee,
                        LocalDateTime.now()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAllEmployees() {

        List<EmployeeResponseDTO> employees =
                employeeService.getAllEmployees();

        ApiResponse<List<EmployeeResponseDTO>> response =
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully",
                        employees,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> searchEmployees(

            @RequestParam String keyword) {

        List<EmployeeResponseDTO> employees =
                employeeService.searchEmployees(keyword);

        ApiResponse<List<EmployeeResponseDTO>> response =
                new ApiResponse<>(

                        true,

                        "Employees searched successfully",

                        employees,

                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> filterEmployees(

            @RequestParam(required = false) String department,

            @RequestParam(required = false) Boolean availability) {

        List<EmployeeResponseDTO> employees =
                employeeService.filterEmployees(
                        department,
                        availability
                );

        ApiResponse<List<EmployeeResponseDTO>> response =
                new ApiResponse<>(

                        true,

                        "Employees filtered successfully",

                        employees,

                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<EmployeeResponseDTO>>> getEmployees(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "experience") String sortBy,

            @RequestParam(defaultValue = "desc") String direction) {

        Page<EmployeeResponseDTO> employees =
                employeeService.getEmployees(
                        page,
                        size,
                        sortBy,
                        direction
                );

        ApiResponse<Page<EmployeeResponseDTO>> response =
                new ApiResponse<>(

                        true,

                        "Employees fetched successfully",

                        employees,

                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getEmployeeById(
            @PathVariable Long id) {

        EmployeeResponseDTO employee =
                employeeService.getEmployeeById(id);

        ApiResponse<EmployeeResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Employee fetched successfully",
                        employee,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        EmployeeResponseDTO employee =
                employeeService.updateEmployee(id, requestDTO);

        ApiResponse<EmployeeResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Employee updated successfully",
                        employee,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        "Employee deleted successfully",
                        null,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<ApiResponse<Void>> assignSkill(
            @PathVariable Long employeeId,
            @PathVariable Long skillId) {

        employeeService.assignSkillToEmployee(employeeId, skillId);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        "Skill assigned successfully",
                        null,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<ApiResponse<Void>> removeSkill(
            @PathVariable Long employeeId,
            @PathVariable Long skillId) {

        employeeService.removeSkillFromEmployee(employeeId, skillId);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        "Skill removed successfully",
                        null,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{employeeId}/skills")
    public ResponseEntity<ApiResponse<List<SkillResponseDTO>>> getEmployeeSkills(
            @PathVariable Long employeeId) {

        List<SkillResponseDTO> skills =
                employeeService.getEmployeeSkills(employeeId);

        ApiResponse<List<SkillResponseDTO>> response =
                new ApiResponse<>(
                        true,
                        "Employee skills fetched successfully",
                        skills,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }
}