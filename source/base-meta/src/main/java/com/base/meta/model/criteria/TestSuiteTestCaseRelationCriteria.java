package com.base.meta.model.criteria;

import com.base.meta.model.TestCase;
import com.base.meta.model.TestSuite;
import com.base.meta.model.TestSuiteTestCaseRelation;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestSuiteTestCaseRelationCriteria implements Serializable {
    private Long testSuiteId;
    private Long testCaseId;

    public Specification<TestSuiteTestCaseRelation> getSpecification() {
        return new Specification<TestSuiteTestCaseRelation>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestSuiteTestCaseRelation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getTestSuiteId() != null) {
                    Join<TestSuiteTestCaseRelation, TestSuite> join = root.join("testSuite", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getTestSuiteId()));
                }
                if (getTestCaseId() != null) {
                    Join<TestSuiteTestCaseRelation, TestCase> join = root.join("testCase", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getTestCaseId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
