package com.base.meta.repository;

import com.base.meta.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends JpaRepository<Project, Long> , JpaSpecificationExecutor<Project> {
    boolean existsByName(String name);
    Project findFirstById(Long id);
}
