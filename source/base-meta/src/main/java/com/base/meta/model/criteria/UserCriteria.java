package com.base.meta.model.criteria;

import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.User;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserCriteria implements Serializable {
    private Long id;
    private Long accountId;
    private String firstName;
    private String lastName;
    private String position;
    private Long statusId;
    private Long positionId;
    private String status;
    private Integer kind;
    private int flag;

    public Specification<User> getSpecification() {
        return new Specification<User>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getAccountId() != null) {
                    Join<User, Account> account = root.join("account", JoinType.INNER);
                    predicates.add(cb.equal(account.get("id"), getAccountId()));
                }
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getFirstName())) {
                    predicates.add(cb.like(cb.lower(root.get("careerName")), "%" + getFirstName().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getLastName())) {
                    predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + getLastName().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getPosition())) {
                    Join<Category, User> join = root.join("position", JoinType.INNER);
                    predicates.add(cb.equal(join.get("name"), getPosition()));
                }
                if (!StringUtils.isEmpty(getStatus())) {
                    Join<Category, User> join = root.join("account", JoinType.INNER).join("status", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getStatus()));
                }
                if(getKind() != null){
                    predicates.add(cb.equal(root.get("account").get("kind"), getKind()));
                }
                if (getFlag() != 0) {
                    predicates.add(cb.equal(root.get("flag"), getFlag()));
                }
                if(getPositionId() != null){
                    Join<Category, User> join = root.join("position", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getPositionId()));
                }
                if(getStatusId() != null){
                    Join<Category, User> join = root.join("account", JoinType.INNER).join("status", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getStatusId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
