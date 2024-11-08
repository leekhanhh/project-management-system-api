package com.base.meta.repository;

import com.base.meta.model.TestDefectComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestDefectCommentRepository extends JpaRepository<TestDefectComment, Long>, JpaSpecificationExecutor<TestDefectComment> {
}
