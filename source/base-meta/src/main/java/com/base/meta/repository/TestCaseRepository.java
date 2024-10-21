package com.base.meta.repository;

import com.base.meta.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> , JpaSpecificationExecutor<TestCase> {
    TestCase findFirstByName(String name);

}
