package com.base.meta.model.criteria;

import com.base.meta.model.Category;
import com.base.meta.model.TestSuiteExecution;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestSuiteExecutionCriteria implements Serializable {
    private Long statusId;
    private Long testSuiteId;
    private Long testExecutionTurnId;
    private int flag;

    public Specification<TestSuiteExecution> getSpecification(){
        return new Specification<TestSuiteExecution>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestSuiteExecution> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (getStatusId() != null) {
                    Join<Category, TestSuiteExecution> join = root.join("status", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getStatusId()));
                }
                if (getTestSuiteId() != null) {
                    Join<Category, TestSuiteExecution> join = root.join("testSuite", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getTestSuiteId()));
                }
                if (getTestExecutionTurnId() != null) {
                    Join<Category, TestSuiteExecution> join = root.join("testExecutionTurn", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getTestExecutionTurnId()));
                }
                if (getFlag() != 0) {
                    predicateList.add(criteriaBuilder.equal(root.get("flag"), getFlag()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
