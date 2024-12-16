package com.base.meta.repository;

import com.base.meta.model.TestCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TestCaseRepository extends JpaRepository<TestCase, Long>, JpaSpecificationExecutor<TestCase> {
    TestCase findFirstByName(String name);
    Optional<TestCase> findFirstById(Long id);
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

    @Query("SELECT tc FROM TestCase tc WHERE tc.id NOT IN (SELECT tstc.testCase.id FROM TestSuiteTestCaseRelation tstc) AND tc.program.id = :programId")
    Page<TestCase> testCaseListExceptThoseAlreadyInTestSuite(@Param("programId") Long programId, Pageable pageable);
}
