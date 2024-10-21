package com.base.meta.repository;

import com.base.meta.model.TestPlanTestSuiteRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestPlanTestSuiteRelationRepository extends JpaRepository<TestPlanTestSuiteRelation, Long>, JpaSpecificationExecutor<TestPlanTestSuiteRelation> {
}
