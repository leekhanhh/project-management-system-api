package com.base.meta.repository;

import com.base.meta.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> , JpaSpecificationExecutor<TestCase> {
    TestCase findFirstByName(String name);

//    @Query("SELECT COUNT(tc.id) " +
//            "FROM TestCase tc " +
//            "JOIN Program p ON p.id = tc.program.id " +
//            "JOIN TestExecution te ON te.program.id = p.id " +
//            "JOIN TestExecutionTurn tet ON tet.testExecution.id = te.id " +
//            "WHERE tet.id = :testExecutionTurnId")
//    Integer countTestCasesByTestExecutionTurnId(@Param("testExecutionTurnId") Long testExecutionTurnId);

    @Query("SELECT " +
            "COUNT(tc.id) AS totalCases, " +
            "SUM(CASE WHEN tce.status.id = (SELECT c.id FROM Category c WHERE c.name = 'Not-Executed' AND c.kind = 7) THEN 1 ELSE 0 END) AS notExecuted, " +
            "SUM(CASE WHEN tce.status.id = (SELECT c.id FROM Category c WHERE c.name = 'Waiting' AND c.kind = 7) THEN 1 ELSE 0 END) AS waiting, " +
            "SUM(CASE WHEN tce.status.id = (SELECT c.id FROM Category c WHERE c.name = 'Completed' AND c.kind = 7) THEN 1 ELSE 0 END) AS completed " +
            "FROM TestCase tc " +
            "INNER JOIN tc.program p " +
            "INNER JOIN TestExecution te ON te.program.id = p.id " +
            "INNER JOIN TestExecutionTurn tet ON te.id = tet.testExecution.id " +
            "INNER JOIN TestCaseExecution tce ON tce.testExecutionTurn.id = tet.id AND tce.testCase.id = tc.id " +
            "WHERE tet.id = :testExecutionTurnId")
    List<Object[]> countTestCasesByExecutionTurnIdWithStatusCounts(@Param("testExecutionTurnId") Long testExecution);


}
