package com.base.meta.repository;

import com.base.meta.model.Group;
import com.base.meta.model.TablePrefix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Group findFirstByName(String name);

    @Query(value = "SELECT *  FROM " + TablePrefix.PREFIX_TABLE + "group g where g.is_system_role = cast(0 as bit) and g.kind = :kind", nativeQuery = true)
    Page<Group> findAllByKind(@Param("kind") int kind, Pageable pageable);

    Group findFirstByKind(int kind);
}
