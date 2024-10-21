package com.base.meta.repository;

import com.base.meta.model.TestExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestExecutionRepository extends JpaRepository<TestExecution, Long>, JpaSpecificationExecutor<TestExecution> {
    TestExecution findByName(String name);
    TestExecution findFirstById(Long id);
}
