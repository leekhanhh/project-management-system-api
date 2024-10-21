package com.base.meta.repository;

import com.base.meta.model.TestDefect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TestDefectRepository extends JpaRepository<TestDefect, Long>, JpaSpecificationExecutor<TestDefect> {
    Optional<TestDefect> findFirstById(Long id);
}
