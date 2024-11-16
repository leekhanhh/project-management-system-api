package com.base.meta.repository;

import com.base.meta.model.TestCaseUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestCaseUploadRepository extends JpaRepository<TestCaseUpload, Long> , JpaSpecificationExecutor<TestCaseUpload> {
    TestCaseUpload findFirstById(Long id);
    @Query("SELECT tcu FROM TestCaseUpload tcu WHERE tcu.testCaseName = :testCaseName")
    List<TestCaseUpload> findAllTestStepByTestCaseName(@Param("testCaseName") String testCaseName);
}
