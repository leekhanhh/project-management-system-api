package com.base.meta.model.criteria;

import com.base.meta.model.Account;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.TestSuite;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestSuiteCriteria implements Serializable {
    private String name;
    private Long accountId;
    private String projectName;
    private Long programId;

    public Specification<TestSuite> getSpecification(){
        return new Specification<TestSuite>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestSuite> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (!StringUtils.isEmpty(getName())) {
                    predicateList.add(criteriaBuilder.like(root.get("name"), "%" + getName() + "%"));
                }
                if (getAccountId() != null) {
                    Join<TestSuite, Account> join = root.join("account", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getAccountId()));
                }
                if (!StringUtils.isEmpty(getProjectName())) {
                    Join<TestSuite, Project> join = root.join("project", JoinType.INNER);
                    predicateList.add(criteriaBuilder.like(join.get("name"), "%" + getProjectName() + "%"));
                }
                if (getProgramId() != null) {
                    Join<TestSuite, Program> join = root.join("program", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getProgramId()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
