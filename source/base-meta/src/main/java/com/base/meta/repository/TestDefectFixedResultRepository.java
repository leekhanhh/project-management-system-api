package com.base.meta.repository;

import com.base.meta.model.TestDefectFixedResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TestDefectFixedResultRepository extends JpaRepository<TestDefectFixedResult, Long>, JpaSpecificationExecutor<TestDefectFixedResult> {
    Optional<TestDefectFixedResult> findFirstById(Long id);
}
