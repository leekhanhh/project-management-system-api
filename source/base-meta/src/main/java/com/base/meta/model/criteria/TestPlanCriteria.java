package com.base.meta.model.criteria;

import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.TestPlan;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TestPlanCriteria implements Serializable {
    private String programName;
    private String name;
    private Date startDate;
    private Date endDate;
    private Long programId;
    private Long projectId;
    private int flag;

    public Specification<TestPlan> getSpecification() {
        return new Specification<TestPlan>() {
            public static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<TestPlan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(getProgramName())) {
                    Join<TestPlan, Program> programJoin = root.join("program");
                    predicates.add(criteriaBuilder.equal(programJoin.get("id"), getProgramName()));
                }
                if (!StringUtils.isEmpty(getName())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (getStartDate() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), getStartDate()));
                }
                if (getEndDate() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), getEndDate()));
                }
                if (getProgramId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("program").get("id"), getProgramId()));
                }
                if (getProjectId() != null) {
                    Join<TestPlan, Program> programJoin = root.join("program", JoinType.INNER);
                    Join<Program, Project> projectJoin = programJoin.join("project", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(projectJoin.get("id"), getProjectId()));
                }
                if (getFlag() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("flag"), getFlag()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
