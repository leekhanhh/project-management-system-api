package com.base.meta.model.criteria;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.model.Category;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryCriteria implements Serializable {
    private Long id;
    private Integer kind;
    private String code;
    private String name;
    private Long parentId;
    private Integer flag;

    public Specification<Category> getSpecification() {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if (!StringUtils.isEmpty(getCode())) {
                    predicates.add(cb.like(cb.lower(root.get("code")), "%" + getCode().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getName())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (getFlag() != null) {
                    predicates.add(cb.equal(root.get("flag"), getFlag()));
                }
                if (getParentId() != null) {
                    Join<Category, Category> parentCategory = root.join("parentCategory", JoinType.INNER);
                    predicates.add(cb.equal(parentCategory.get("id"), getParentId()));
                } else {
                    if (getKind() != null && (getKind().equals(BaseMetaConstant.CATEGORY_KIND_PROJECT)
                            || getKind().equals(BaseMetaConstant.CATEGORY_KIND_REQUIREMENT)
                            || getKind().equals(BaseMetaConstant.CATEGORY_KIND_PROGRAM)
                            || getKind().equals(BaseMetaConstant.CATEGORY_KIND_TEST_EXECUTION)
                            || getKind().equals(BaseMetaConstant.CATEGORY_KIND_TEST_SUITE_EXECUTION)
                            || getKind().equals(BaseMetaConstant.CATEGORY_KIND_TEST_CASE_EXECUTION)
                            || getKind().equals(BaseMetaConstant.CATEGORY_KIND_TEST_STEP_EXECUTION)
                            || getKind().equals(BaseMetaConstant.CATEGORY_KIND_TEST_DEFECT))) {
                        predicates.add(cb.isNull(root.get("parentCategory")));
                    }
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}