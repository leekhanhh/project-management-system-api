package com.base.meta.repository;

import com.base.meta.model.ProjectMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> , JpaSpecificationExecutor<ProjectMember> {
    ProjectMember findByAccountId(Long accountId);

    Page<ProjectMember> findAllByAccountId(Long accountId, Pageable pageable);
}
