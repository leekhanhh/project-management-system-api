package com.base.meta.repository;

import com.base.meta.model.TestStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TestStepRepository extends JpaRepository<TestStep, Long> , JpaSpecificationExecutor<TestStep> {
    Optional<TestStep> findFirstById(Long id);
    List<TestStep> findAllByTestCaseId(Long testCaseId);
    Optional<TestStep> findFirstByTestCaseAndStepNumber(Long testCaseId, Integer stepNumber);
}
