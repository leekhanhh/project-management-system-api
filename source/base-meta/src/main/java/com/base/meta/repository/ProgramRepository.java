package com.base.meta.repository;

import com.base.meta.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProgramRepository extends JpaRepository<Program, Long>, JpaSpecificationExecutor<Program> {
    boolean existsProgramByNameAndProjectId(String name, Long projectId);
    Program findFirstById(Long id);
    Program findFirstByName(String name);
}
