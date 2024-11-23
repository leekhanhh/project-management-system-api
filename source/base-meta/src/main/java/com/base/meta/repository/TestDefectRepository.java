package com.base.meta.repository;

import com.base.meta.model.TestDefect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TestDefectRepository extends JpaRepository<TestDefect, Long>, JpaSpecificationExecutor<TestDefect> {
    Optional<TestDefect> findFirstById(Long id);
    @Query(value = "SELECT DISTINCT dpmp.dev_account_id, dpmp.tester_account_id, dpmp.owner_account_id " +
            "FROM db_project_management_test_defect_comment dpmtdc " +
            "INNER JOIN db_project_management_test_defect dpmtd ON dpmtdc.test_step_execution_id = dpmtd.id " +
            "INNER JOIN db_project_management_test_step_execution dpmtse ON dpmtd.test_step_execution_id = dpmtse.id " +
            "INNER JOIN db_project_management_test_case_execution dpmtce ON dpmtce.id = dpmtse.test_case_execution_id " +
            "INNER JOIN db_project_management_test_execution_turn dpmtet ON dpmtet.id = dpmtce.test_execution_turn_id " +
            "INNER JOIN db_project_management_test_execution dpmte ON dpmte.id = dpmtet.test_execution_id " +
            "INNER JOIN db_project_management_program dpmp ON dpmp.id = dpmte.program_id " +
            "WHERE dpmtd.id = :defectId", nativeQuery = true)
    List<Long> findDevTesterOwnerAccountsByDefectId(@Param("defectId") Long defectId);
}
