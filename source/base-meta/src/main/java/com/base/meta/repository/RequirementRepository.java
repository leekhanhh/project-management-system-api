package com.base.meta.repository;

import com.base.meta.model.Requirement;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RequirementRepository extends JpaRepository<Requirement, Long> , JpaSpecificationExecutor<Requirement> {
    Requirement findFirstById(Long id);
    Requirement findFirstByName(String name);
}
