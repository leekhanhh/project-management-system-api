package com.base.meta.repository;

import com.base.meta.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Page<Category> findAllByParentCategoryIsNull(Specification<Category> specification, Pageable pageable);

    Category findByNameAndKind(String name, Integer kind);

    Category findFirstByCode(String code);
    Category findFirstByCodeAndKind(String code, Integer kind);

    Category findFirstById(Long id);
    boolean existsByKind(Integer kind);
}
