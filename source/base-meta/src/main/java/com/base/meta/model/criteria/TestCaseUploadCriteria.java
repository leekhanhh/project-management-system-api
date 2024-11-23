package com.base.meta.model.criteria;

import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.TestCaseUpload;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestCaseUploadCriteria implements Serializable {
    private String testCaseName;
    private Long programId;

    public Specification<TestCaseUpload> getSpecification() {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestCaseUpload> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(getTestCaseName())) {
                    predicates.add(cb.like(cb.lower(root.get("testCaseName")), "%" + getTestCaseName().toLowerCase() + "%"));
                }
                if (getProgramId() != null) {
                    Join<TestCaseUpload, Program> program = root.join("program", JoinType.INNER);
                    predicates.add(cb.equal(program.get("id"), getProgramId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
