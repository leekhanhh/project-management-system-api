package com.base.meta.dto.testsuite;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.dto.testplan.TestPlanDto;
import lombok.Data;

@Data
public class TestSuiteDto extends ABasicAdminDto {
    private Long id;
    private String name;
    private String description;
    private Integer testCaseCount;
    private TestPlanDto testPlan;
    private AccountDto account;
}
