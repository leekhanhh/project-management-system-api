package com.base.meta.model.criteria;

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
    private String projectName;
    private Long projectId;
    private String devision;
    private String status;

    public Specification<Requirement> getSpecification() {
        return new Specification<Requirement>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Requirement> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(getName())) {
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + getName().toLowerCase() + "%");
                }
                if (!StringUtils.isEmpty(getProjectName())) {
                    Join<Requirement, Project> project = root.join("project", JoinType.INNER);
                    return criteriaBuilder.like(criteriaBuilder.lower(project.get("name")), "%" + getProjectName().toLowerCase() + "%");
                }
                if (getProjectId() != null) {
                    Join<Requirement, Project> project = root.join("project", JoinType.INNER);
                    return criteriaBuilder.equal(project.get("id"), getProjectId());
                }
                if (!StringUtils.isEmpty(getDevision())) {
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("devision")), "%" + getDevision().toLowerCase() + "%");
                }
                if (getStatus() != null) {
                    return criteriaBuilder.equal(root.get("flag"), getStatus());
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
