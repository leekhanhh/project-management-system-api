package com.base.meta.repository;

import com.base.meta.model.TestCaseExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TestCaseExecutionRepository extends JpaRepository<TestCaseExecution, Long>, JpaSpecificationExecutor<TestCaseExecution> {
    Optional<TestCaseExecution> findFirstById(Long id);
    List<TestCaseExecution> findAllByTestCaseId(Long testCaseId);
    List<TestCaseExecution> findAllByStatusId(Long statusId);
    List<TestCaseExecution> findAllByTestExecutionTurnId(Long testExecutionTurnId);

    @Query("SELECT count(t) FROM TestCaseExecution t where t.testExecutionTurn.id = :testExecutionTurnId")
    Integer countByTestExecutionTurnId(Long testExecutionTurnId);

    @Query("SELECT count(t) FROM TestCaseExecution t where t.testExecutionTurn.id = :testExecutionTurnId and t.status.id = :statusId")
    Integer countByTestExecutionTurnIdAndStatusId(Long testExecutionTurnId, Long statusId);
}
