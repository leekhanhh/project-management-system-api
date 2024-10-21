package com.base.meta.model.criteria;

import com.base.meta.model.TestPlan;
import com.base.meta.model.TestPlanTestSuiteRelation;
import com.base.meta.model.TestSuite;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

@Data
public class TestPlanTestSuiteRelationCriteria implements Serializable {
    private Long testPlanId;
    private Long testSuiteId;

    public Specification<TestPlanTestSuiteRelation> getSpecification() {
        return new Specification<TestPlanTestSuiteRelation>() {
            public static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<TestPlanTestSuiteRelation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new java.util.ArrayList<>();
                if (getTestPlanId() != null) {
                    Join<TestPlan, TestPlanTestSuiteRelationCriteria> testPlanJoin = root.join("testPlan", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(testPlanJoin.get("id"), getTestPlanId()));
                }
                if (getTestSuiteId() != null) {
                    Join<TestSuite, TestPlanTestSuiteRelationCriteria> testSuiteJoin = root.join("testSuite", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(testSuiteJoin.get("id"), getTestSuiteId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
