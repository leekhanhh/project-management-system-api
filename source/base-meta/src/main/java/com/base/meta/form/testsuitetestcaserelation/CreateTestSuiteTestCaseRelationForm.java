package com.base.meta.form.testsuitetestcaserelation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTestSuiteTestCaseRelationForm {
    @ApiModelProperty(value = "test suite id", required = true)
    @NotNull(message = "testSuiteId must not be null!")
    private Long testSuiteId;
    @ApiModelProperty(value = "test case id", required = true)
    @NotNull(message = "testCaseId must not be null!")
    private Long testCaseId;
}
