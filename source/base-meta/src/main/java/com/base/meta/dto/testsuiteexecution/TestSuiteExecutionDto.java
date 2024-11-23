package com.base.meta.dto.testsuiteexecution;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.testexecutionturn.TestExecutionTurnDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import lombok.Data;

@Data
public class TestSuiteExecutionDto extends ABasicAdminDto {
    private Long id;
    private Integer orderNumber;
    private CategoryDto status;
    private TestSuiteDto testSuite;
    private TestExecutionTurnDto testExecutionTurn;
    private String displayId;
}
