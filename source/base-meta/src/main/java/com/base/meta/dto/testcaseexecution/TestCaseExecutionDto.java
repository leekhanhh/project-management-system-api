package com.base.meta.dto.testcaseexecution;

import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.dto.testexecutionturn.TestExecutionTurnDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import lombok.Data;

@Data
public class TestCaseExecutionDto {
    private Long id;
    private TestExecutionTurnDto testExecutionTurn;
    private TestCaseDto testCase;
    private TestSuiteDto testSuite;
    private CategoryDto status;
    private CategoryDto testExecutionTypeCode;
}
