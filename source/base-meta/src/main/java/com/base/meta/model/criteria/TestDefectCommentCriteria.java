package com.base.meta.model.criteria;

import com.base.meta.model.TestDefectComment;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;

@Data
public class TestDefectCommentCriteria implements Serializable {
    private Long testDefectId;
    private Long senderId;
    private int flag;

    public Specification<TestDefectComment> getSpecification() {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.and(
                    testDefectId == null ? cb.conjunction() : cb.equal(root.get("testDefect").get("id"), testDefectId),
                    senderId == null ? cb.conjunction() : cb.equal(root.get("sender").get("id"), senderId),
                    flag == 0 ? cb.conjunction() : cb.equal(root.get("flag"), flag)
            );
        };
    }
}
