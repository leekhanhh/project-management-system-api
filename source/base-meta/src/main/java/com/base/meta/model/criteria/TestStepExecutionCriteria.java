package com.base.meta.model.criteria;

import com.base.meta.model.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

@Data
public class TestStepExecutionCriteria implements Serializable {
    private Long testStepId;
    private Long testCaseExecutionId;
    private Long statusId;
    private Boolean isDefected;

    public Specification<TestStepExecution> getSpecification(){
        return new Specification<TestStepExecution>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestStepExecution> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new java.util.ArrayList<>();
                if (getTestStepId() != null) {
                    Join<TestStep, TestStepExecution> join = root.join("testStep", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getTestStepId()));
                }
                if (getTestCaseExecutionId() != null) {
                    Join<TestCaseExecution, TestStepExecution> join = root.join("testCaseExecution", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getTestCaseExecutionId()));
                }
                if (getStatusId() != null) {
                    Join<Category, TestStepExecution> join = root.join("status", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getStatusId()));
                }
                if (getIsDefected() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("isDefected"), getIsDefected()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
