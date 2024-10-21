package com.base.meta.dto.testsuitetestcaseralation;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import lombok.Data;

@Data
public class TestSuiteTestCaseRelationDto extends ABasicAdminDto {
    private Long id;
    private TestSuiteDto testSuite;
    private TestCaseDto testCase ;
}
