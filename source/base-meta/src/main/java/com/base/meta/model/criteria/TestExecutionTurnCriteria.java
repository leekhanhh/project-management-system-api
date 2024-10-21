package com.base.meta.model.criteria;

import com.base.meta.model.Account;
import com.base.meta.model.TestExecutionTurn;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TestExecutionTurnCriteria implements Serializable {
    private String developerName;
    private Long testExecutionId;
    private Date startDate;
    private Date endDate;

    public Specification<TestExecutionTurn> getSpecification() {
        return new Specification<TestExecutionTurn>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestExecutionTurn> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if(!StringUtils.isEmpty(getDeveloperName())) {
                    Join<Account, TestExecutionTurn> join = root.join("assignedDeveloper", JoinType.INNER);
                    predicateList.add(criteriaBuilder.like(join.get("fullname"), "%" + getDeveloperName() + "%"));
                }
                if(getTestExecutionId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("testExecution").get("id"), getTestExecutionId()));
                }
                if(getStartDate() != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), getStartDate()));
                }
                if(getEndDate() != null) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), getEndDate()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
