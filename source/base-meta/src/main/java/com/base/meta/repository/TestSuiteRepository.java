package com.base.meta.repository;

import com.base.meta.model.TestSuite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestSuiteRepository extends JpaRepository<TestSuite, Long>, JpaSpecificationExecutor<TestSuite> {
    TestSuite findFirstById(Long id);
    TestSuite findFirstByName(String name);
    boolean existsByProgramIdAndName(Long programId, String name);
}
