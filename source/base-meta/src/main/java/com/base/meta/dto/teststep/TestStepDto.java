package com.base.meta.dto.teststep;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.model.TestCase;
import lombok.Data;

@Data
public class TestStepDto extends ABasicAdminDto {
    private Long id;
    private Integer stepNumber;
    private String action;
    private String data;
    private String expectedResult;
    private TestCaseDto testCase;
    private String displayId;
}
