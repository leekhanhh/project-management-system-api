package com.base.meta.repository;

import com.base.meta.model.TestSuiteExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestSuiteExecutionRepository extends JpaRepository<TestSuiteExecution, Long>, JpaSpecificationExecutor<TestSuiteExecution> {
    TestSuiteExecution findFirstById(Long id);
}
