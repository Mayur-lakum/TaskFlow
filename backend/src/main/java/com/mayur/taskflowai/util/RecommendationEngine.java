package com.mayur.taskflowai.util;

import com.mayur.taskflowai.entity.Employee;
import com.mayur.taskflowai.entity.Project;
import com.mayur.taskflowai.entity.Skill;

public class RecommendationEngine {

    private RecommendationEngine() {
    }

    public static int calculateScore(Employee employee, Project project) {

        int score = 0;

        // ==========================================
        // 60 Marks - Skill Matching
        // ==========================================

        if (!project.getRequiredSkills().isEmpty()) {

            long matchedSkills = employee.getSkills()
                    .stream()
                    .filter(project.getRequiredSkills()::contains)
                    .count();

            double percentage =
                    (double) matchedSkills / project.getRequiredSkills().size();

            score += (int) Math.round(percentage * 60);
        }

        // ==========================================
        // 20 Marks - Experience
        // ==========================================

        if (employee.getExperience() >= project.getRequiredExperience()) {

            score += 20;

        } else if (employee.getExperience() > 0) {

            score += 10;
        }

        // ==========================================
        // 10 Marks - Availability
        // ==========================================

        if (Boolean.TRUE.equals(employee.getAvailability())) {

            score += 10;
        }

        // ==========================================
        // 10 Marks - Performance
        // ==========================================

        if (employee.getPerformanceScore() != null) {

            double performance = employee.getPerformanceScore();

            // Convert 0-100 to 0-10 if needed
            if (performance > 10) {
                performance = performance / 10.0;
            }

            score += (int) Math.round(performance);
        }

        return Math.min(score, 100);
    }
}