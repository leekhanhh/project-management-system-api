package com.base.meta.repository;

import com.base.meta.model.TestExecutionTurn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TestExecutionTurnRepository extends JpaRepository<TestExecutionTurn, Long>, JpaSpecificationExecutor<TestExecutionTurn> {
    @Query("SELECT count(t) FROM TestExecutionTurn t WHERE t.testExecution.id = :testExecutionId")
    Integer countTestExecutionTurnByTestExecution(@Param("testExecutionId") Long testExecutionId);

    @Query("SELECT t.testExecution.id, COUNT(t) FROM TestExecutionTurn t WHERE t.testExecution.id IN :testExecutionIds GROUP BY t.testExecution.id")
    Map<Long, Integer> countTestExecutionTurnByTestExecutionIds(@Param("testExecutionIds") List<Long> testExecutionIds);

    TestExecutionTurn findFirstByTurnNumber(Integer turnNumber);
    TestExecutionTurn findFirstById(Long id);



}
