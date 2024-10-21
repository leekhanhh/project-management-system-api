package com.base.meta.repository;

import com.base.meta.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> , JpaSpecificationExecutor<ProjectMember> {
    ProjectMember findByAccountId(Long accountId);
}
