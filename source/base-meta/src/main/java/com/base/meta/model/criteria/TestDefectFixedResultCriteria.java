package com.base.meta.model.criteria;

import com.base.meta.model.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

@Data
public class TestDefectFixedResultCriteria implements Serializable {
    private Long testDefectId;
    private int flag;
    public Specification<TestDefectFixedResult> getSpecification() {
        return new Specification<TestDefectFixedResult> () {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestDefectFixedResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new java.util.ArrayList<>();
                if (getTestDefectId() != null) {
                    Join<TestDefectFixedResult, TestDefect> join = root.join("testDefect", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getTestDefectId()));
                }
                if (getFlag() != 0) {
                    predicateList.add(criteriaBuilder.equal(root.get("flag"), getFlag()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
