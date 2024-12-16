package com.base.meta.model.criteria;

import com.base.meta.model.Category;
import com.base.meta.model.Project;
import com.base.meta.model.Requirement;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RequirementCriteria implements Serializable {
    private String name;
    private Long projectId;
    private Long acceptanceId;
    private Integer flag;
    private Long divisionId;
    private Long detailClassificationId;


    public Specification<Requirement> getSpecification() {
        return new Specification<Requirement>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Requirement> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(getName())) {
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + getName().toLowerCase() + "%");
                }
                if (getProjectId() != null) {
                    Join<Project, Requirement> join = root.join("project", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getProjectId()));
                }
                if (getDivisionId() != null) {
                    Join<Category, Requirement> join = root.join("division", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getDivisionId()));
                }
                if (getDetailClassificationId() != null) {
                    Join<Category, Requirement> join = root.join("detailClassification", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getDetailClassificationId()));
                }
                if (getAcceptanceId() != null) {
                    Join<Category, Requirement> join = root.join("acceptance", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getAcceptanceId()));
                }
                if (getFlag() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("flag"), getFlag()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
