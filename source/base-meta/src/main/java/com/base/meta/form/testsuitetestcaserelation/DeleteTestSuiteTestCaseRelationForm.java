package com.base.meta.form.testsuitetestcaserelation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteTestSuiteTestCaseRelationForm {
    @ApiModelProperty(value = "Test Suite ID")
    @NotNull(message = "Test Suite ID is required")
    private Long testSuiteId;
    @ApiModelProperty(value = "Test Case ID")
    @NotNull(message = "Test Case ID is required")
    private Long testCaseId;
}
