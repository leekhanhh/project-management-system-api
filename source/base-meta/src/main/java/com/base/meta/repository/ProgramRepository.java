package com.base.meta.repository;

import com.base.meta.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProgramRepository extends JpaRepository<Program, Long>, JpaSpecificationExecutor<Program> {
    Program findFirstByName(String name);
    Program findFirstById(Long id);
}
