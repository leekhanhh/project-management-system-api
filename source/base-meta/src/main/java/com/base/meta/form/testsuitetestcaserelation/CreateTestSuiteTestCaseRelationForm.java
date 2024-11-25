package com.base.meta.form.testsuitetestcaserelation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateTestSuiteTestCaseRelationForm {
    @ApiModelProperty(value = "test suite id", required = true)
    @NotNull(message = "testSuiteId must not be null!")
    private Long testSuiteId;
    @ApiModelProperty(value = "test case id list", required = true)
    @NotNull(message = "test case list must not be null!")
    private List<Long> testCaseIds;
}
