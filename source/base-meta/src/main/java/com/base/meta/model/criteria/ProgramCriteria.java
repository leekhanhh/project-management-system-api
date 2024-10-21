package com.base.meta.model.criteria;

import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.Program;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ProgramCriteria implements Serializable {
    private String programName;
    private Date startDate;
    private Date endDate;
    private Long programTypeId;
    private Long programStatusId;
    private String programCategory;
    private String programOwnerName;
    private String assignedDeveloperName;
    private String assignedTesterName;

    public Specification<Program> getSpecification() {
        return new Specification<Program>() {
            public static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Program> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(getProgramName())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("programName")), "%" + getProgramName().toLowerCase() + "%"));
                }
                if (getStartDate() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), getStartDate()));
                }
                if (getEndDate() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), getEndDate()));
                }
                if (getProgramTypeId() != null) {
                    Join<Category, Program> join = root.join("programType", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("name"), getProgramTypeId()));
                }
                if (getProgramOwnerName() != null) {
                    Join<Account, Program> join = root.join("programOwner", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("fullName"), getProgramOwnerName()));
                }
                if (getAssignedDeveloperName() != null) {
                    Join<Account, Program> join = root.join("assignedDeveloper", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("fullName"), getAssignedDeveloperName()));
                }
                if (getAssignedTesterName() != null) {
                    Join<Account, Program> join = root.join("assignedTester", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getAssignedTesterName()));
                }
                if (getProgramStatusId() != null) {
                    Join<Category, Program> join = root.join("programStatus", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("name"), getProgramStatusId()));
                }
                if (getProgramCategory() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("programCategory"), getProgramCategory()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
