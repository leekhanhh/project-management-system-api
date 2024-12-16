package com.base.meta.model.criteria;

import com.base.meta.model.Account;
import com.base.meta.model.Project;
import com.base.meta.model.ProjectMember;
import com.base.meta.utils.StringUtils;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectMemberCriteria implements Serializable {
    private Long projectId;
    private Long accountId;
    private int flag;

    public Specification<ProjectMember> getSpecification(){
        return new Specification<ProjectMember>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<ProjectMember> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(projectId != null){
                    Join<ProjectMember, Project> project = root.join("project", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(project.get("id"), projectId));
                }
                if(accountId != null){
                    Join<ProjectMember, Account> account = root.join("account", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(account.get("id"), accountId));
                }
                if(flag != 0){
                    predicates.add(criteriaBuilder.equal(root.get("flag"), flag));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

    }
}
