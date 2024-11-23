package com.base.meta.repository;

import com.base.meta.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Category findFirstByKind(Integer kind);

    Category findByNameAndKind(String name, Integer kind);

    Category findFirstByCode(String code);
    Category findFirstByCodeAndKind(String code, Integer kind);

    Category findFirstById(Long id);
}
