package com.base.meta.model.criteria;

import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.TestDefect;
import com.base.meta.model.TestStepExecution;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestDefectCriteria implements Serializable {
    private String name;
    private String priority;
    private Long statusId;
    private String assignedDeveloperName;
    private Long testStepExecutionId;

    public Specification<TestDefect> getSpecification() {
        return new Specification<TestDefect>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestDefect> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if(!StringUtils.isEmpty(getName())) {
                    predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if(!StringUtils.isEmpty(getPriority())) {
                    predicateList.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("priority")), "%" + getPriority().toLowerCase() + "%"));
                }
                if(getStatusId() != null) {
                   Join<Category, TestDefect> join = root.join("status", JoinType.INNER);
                     predicateList.add(criteriaBuilder.equal(join.get("id"), getStatusId()));
                }
                if(!StringUtils.isEmpty(getAssignedDeveloperName())) {
                    Join<Account, TestDefect> join = root.join("assignedDeveloper", JoinType.INNER);
                    predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(join.get("fullName")), "%" + getAssignedDeveloperName().toLowerCase() + "%"));
                }
                if(getTestStepExecutionId() != null) {
                    Join<TestStepExecution, TestDefect> join = root.join("testStepExecution", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getTestStepExecutionId()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
