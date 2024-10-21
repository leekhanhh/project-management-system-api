package com.base.meta.repository;

import com.base.meta.model.TestCaseUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestCaseUploadRepository extends JpaRepository<TestCaseUpload, Long> , JpaSpecificationExecutor<TestCaseUpload> {
    TestCaseUpload findFirstById(Long id);
}
