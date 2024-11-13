package com.base.meta.model.criteria;

import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.Project;
import com.base.meta.model.TestExecution;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TestExecutionCriteria implements Serializable {
    private String name;
    private Long categoryId;
    private Long statusId;
    private String assignedDeveloperName;
    private Long projectId;
    private Date planStartDate;
    private Date planEndDate;

    public Specification<TestExecution> getSpecification() {
        return new Specification<TestExecution>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestExecution> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getName() != null) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (getCategoryId() != null) {
                    Join<Category, TestExecution> categoryJoin = root.join("category");
                    predicates.add(criteriaBuilder.equal(categoryJoin.get("id"), getCategoryId()));
                }
                if (getStatusId() != null) {
                    Join<Category, TestExecution> statusJoin = root.join("status");
                    predicates.add(criteriaBuilder.equal(statusJoin.get("id"), getStatusId()));
                }
                if (getAssignedDeveloperName() != null) {
                    Join<Account, TestExecution> assignedDeveloper = root.join("assignedDeveloper");
                    predicates.add(criteriaBuilder.equal(assignedDeveloper.get("fullName"), getAssignedDeveloperName()));
                }
                if (getProjectId() != null) {
                    Join<Project, TestExecution> project = root.join("project");
                    predicates.add(criteriaBuilder.equal(project.get("id"), getProjectId()));
                }
                if (getPlanStartDate() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("planStartDate"), getPlanStartDate()));
                }
                if (getPlanEndDate() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("planEndDate"), getPlanEndDate()));
                }
                if(getPlanStartDate() != null && getPlanEndDate() != null) {
                    predicates.add(criteriaBuilder.between(root.get("planStartDate"), getPlanStartDate(), getPlanEndDate()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
