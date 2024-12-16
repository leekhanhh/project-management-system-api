package com.base.meta.model.criteria;

import com.base.meta.model.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestCaseExecutionCriteria implements Serializable {
    private Long testExecutionTurnId;
    private Long testCaseId;
    private Long testSuiteId;
    private Long categoryId;
    private Long testExecutionTypeCodeId;
    private int flag;

    public Specification<TestCaseExecution> getSpecification() {
        return new Specification<TestCaseExecution>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestCaseExecution> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getTestExecutionTurnId() != null) {
                    Join<TestExecutionTurn, TestCaseExecution> join = root.join("testExecutionTurn", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getTestExecutionTurnId()));
                }
                if (getTestCaseId() != null) {
                    Join<TestCase, TestCaseExecution> join = root.join("testCase", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getTestCaseId()));
                }
                if (getTestSuiteId() != null) {
                    Join<TestSuite, TestCaseExecution> join = root.join("testSuite", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getTestSuiteId()));
                }
                if (getCategoryId() != null) {
                    Join<Category, TestCaseExecution> join = root.join("status", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getCategoryId()));
                }
                if (getTestExecutionTypeCodeId() != null) {
                    Join<Category, TestCaseExecution> join = root.join("testExecutionTypeCode", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getTestExecutionTypeCodeId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
