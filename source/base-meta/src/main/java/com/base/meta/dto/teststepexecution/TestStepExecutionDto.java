package com.base.meta.dto.teststepexecution;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.testcaseexecution.TestCaseExecutionDto;
import com.base.meta.dto.teststep.TestStepDto;
import lombok.Data;

@Data
public class TestStepExecutionDto extends ABasicAdminDto {
    private Long id;
    private TestStepDto testStep;
    private TestCaseExecutionDto testCaseExecution;
    private CategoryDto status;
    private Boolean isDefected;

}
