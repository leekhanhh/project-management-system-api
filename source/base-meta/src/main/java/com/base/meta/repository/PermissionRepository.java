package com.base.meta.repository;

import com.base.meta.model.Permission;
import com.base.meta.model.TablePrefix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    Permission findFirstByName(String name);
    Boolean existsByPermissionCode(String permissionCode);

    Permission findByPermissionCode(String permissionCode);

    @Query(value = "SELECT p.* " +
            "FROM " + TablePrefix.PREFIX_TABLE + "permission p " +
            "JOIN " + TablePrefix.PREFIX_TABLE + "permission_group g on p.id = g.permission_id " +
            "JOIN " + TablePrefix.PREFIX_TABLE + "account a on a.group_id = g.group_id " +
            "WHERE a.kind =:userKind", nativeQuery = true)
    List<Permission> findAllByUserKind(@Param("userKind") Integer userKind);
}
