package com.base.meta.form.testplantestsuiterelation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateTestPlanTestSuiteRelationForm {
    @ApiModelProperty(value = "Test Plan ID", required = true)
    @NotNull(message = "Test Plan ID cannot be null")
    private Long testPlanId;
    @ApiModelProperty(value = "Test Suite ID", required = true)
    @NotNull(message = "Test Suite ID cannot be null")
    private List<Long> testSuiteIds;
}
