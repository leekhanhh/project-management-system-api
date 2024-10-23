package com.base.meta.repository;

import com.base.meta.model.TestSuiteTestCaseRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TestSuiteTestCaseRelationRepository extends JpaRepository<TestSuiteTestCaseRelation, Long>, JpaSpecificationExecutor<TestSuiteTestCaseRelation> {
    TestSuiteTestCaseRelation findByTestSuiteIdAndTestCaseId(Long testSuiteId, Long testCaseId);
    @Query("SELECT t.testSuite.id, COUNT(t) FROM TestSuiteTestCaseRelation t WHERE t.testSuite.id IN :testSuiteIds GROUP BY t.testSuite.id")
    Map<Long, Integer> countTestSuiteTestCaseRelationsByTestSuiteIds(@Param("testSuiteIds") List<Long> testSuiteIds);

    @Query("SELECT count(t) FROM TestSuiteTestCaseRelation t WHERE t.testSuite.id = :testSuiteId")
    Integer countByTestSuiteId(@Param("testSuiteId") Long testSuiteId);
}
