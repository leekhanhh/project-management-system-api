package com.base.meta.dto.testplantestsuiterelation;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.testplan.TestPlanDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import lombok.Data;

@Data
public class TestPlanTestSuiteRelationDto extends ABasicAdminDto {
    private Long id;
    private TestPlanDto testPlan;
    private TestSuiteDto testSuite;
}
