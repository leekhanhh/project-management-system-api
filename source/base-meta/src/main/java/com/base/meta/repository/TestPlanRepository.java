package com.base.meta.repository;

import com.base.meta.model.TestPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestPlanRepository extends JpaRepository<TestPlan,Long>, JpaSpecificationExecutor<TestPlan> {
    TestPlan findFirstById(Long id);
    TestPlan findFirstByName(String name);
}
