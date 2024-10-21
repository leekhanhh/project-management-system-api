package com.base.meta.repository;

import com.base.meta.model.TestExecutionTurn;
import com.base.meta.model.TestStepExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TestStepExecutionRepository extends JpaRepository<TestStepExecution, Long>, JpaSpecificationExecutor<TestStepExecution> {
    Optional<TestStepExecution> findFirstById(Long id);
    @Query("SELECT count(t) FROM TestStepExecution t WHERE t.isDefected = true")
    Integer countByIsDefected();
}
