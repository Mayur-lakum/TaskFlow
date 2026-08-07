package com.mayur.taskflowai.repository;

import com.mayur.taskflowai.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByProjectCode(String projectCode);

}