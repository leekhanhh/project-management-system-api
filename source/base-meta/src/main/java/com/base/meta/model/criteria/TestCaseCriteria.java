package com.base.meta.model.criteria;

import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.TestCase;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class TestCaseCriteria implements Serializable {
    private String name;
    private Long programId;

    public Specification<TestCase> getSpecification(){
        return new Specification<TestCase>() {
            public static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestCase> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getName() != null) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if(getProgramId() != null){
                    Join<Program,TestCase> join = root.join("program", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("id"), getProgramId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
