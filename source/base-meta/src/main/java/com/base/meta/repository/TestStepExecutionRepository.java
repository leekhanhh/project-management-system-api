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
    @Query("SELECT count(t) FROM TestStepExecution t WHERE t.isDefected = true AND t.testCaseExecution.testExecutionTurn.id = :testExecutionTurnId")
    Integer countByIsDefected(@Param("testExecutionTurnId") Long testExecutionTurnId);

    @Query("SELECT tc.name AS test_case_name, " +
            "p.id AS program_name, " +
            "COUNT(tse.id) AS total_cases, " +
            "SUM(CASE WHEN c.name = 'Not-executed' AND td.assignedDeveloper.id = :assignedDevId THEN 1 ELSE 0 END) AS not_executed_cases, " +
            "SUM(CASE WHEN c.name = 'Pass' AND td.assignedDeveloper.id = :assignedDevId THEN 1 ELSE 0 END) AS pass_cases, " +
            "SUM(CASE WHEN c.name = 'Fail' AND td.assignedDeveloper.id = :assignedDevId THEN 1 ELSE 0 END) AS fail_cases " +
            "FROM TestStepExecution tse INNER JOIN TestCaseExecution tce ON tse.testCaseExecution.id = tce.id " +
            "RIGHT JOIN TestCase tc ON tc.id = tce.testCase.id " +
            "INNER JOIN TestDefect td ON td.testStepExecution.id = tse.id " +
            "INNER JOIN Category c ON tse.status.id = c.id " +
            "INNER JOIN Program p ON p.id = tc.program.id " +
            "WHERE p.id = :programId " +
            "GROUP BY tc.name, p.name")
    Integer countCasesByAccountId(@Param("programId") Long programId, @Param("assignedDevId") Long accountId);

}
