package com.base.meta.form.testsuiteexecution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateTestSuiteExecutionForm {
    @NotNull(message = "Id is required")
    @ApiModelProperty(value = "Test suite execution id", required = true)
    private Long id;
    @ApiModelProperty(value = "Test suite execution order number", required = true)
    private Integer orderNumber;
    @ApiModelProperty(value = "Test suite execution status id", required = true)
    private Long statusId;
    @ApiModelProperty(value = "Test suite execution flag")
    private Integer flag;
}
