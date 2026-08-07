package com.mayur.taskflowai.dto.response;

public class RecommendationResponseDTO {

    private Long employeeId;

    private String employeeName;

    private String employeeCode;

    private String department;

    private int matchedSkills;

    private int experience;

    private int score;

    public RecommendationResponseDTO() {
    }

    public RecommendationResponseDTO(Long employeeId,
                                     String employeeName,
                                     String employeeCode,
                                     String department,
                                     int matchedSkills,
                                     int experience,
                                     int score) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeCode = employeeCode;
        this.department = department;
        this.matchedSkills = matchedSkills;
        this.experience = experience;
        this.score = score;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(int matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}