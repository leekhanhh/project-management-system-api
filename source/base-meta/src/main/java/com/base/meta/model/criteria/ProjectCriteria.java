package com.base.meta.model.criteria;

import com.base.meta.model.Category;
import com.base.meta.model.Project;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ProjectCriteria implements Serializable {
    private String name;
    private Long statusId;
    private Date startDate;
    private Date endDate;
    private int flag;

    public Specification<Project> getSpecification() {
        return new Specification<Project>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(getName())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (getStatusId() != null) {
                    Join<Category, Project> join = root.join("status", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getStatusId()));
                }
                if (getStartDate() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), getStartDate()));
                }
                if (getEndDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), getEndDate()));
                }
                if(flag != 0){
                    predicates.add(cb.equal(root.get("flag"), getFlag()));
                }
                query.orderBy(cb.desc(root.get("createdDate")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
