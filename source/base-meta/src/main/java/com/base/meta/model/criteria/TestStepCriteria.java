package com.base.meta.model.criteria;

import com.base.meta.model.TestCase;
import com.base.meta.model.TestStep;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestStepCriteria implements Serializable {
    private Long testcaseId;
    private Integer stepNumber;
    private int flag;

    public Specification<TestStep> getSpecification() {
        return new Specification<TestStep>() {
            public static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<TestStep> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getTestcaseId() != null) {
                    Join<TestStep, TestCase> join = root.join("testCase", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getTestcaseId()));
                }
                if (getStepNumber() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("stepNumber"), getStepNumber()));
                }
                if (getFlag() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("flag"), getFlag()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
