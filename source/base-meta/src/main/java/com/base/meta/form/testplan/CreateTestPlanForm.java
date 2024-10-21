package com.base.meta.form.testplan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateTestPlanForm {
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "name cannot be null")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "programId cannot be null")
    @ApiModelProperty(name = "programId")
    private Long programId;
    @ApiModelProperty(name = "startDate", required = true)
    @NotNull(message = "startDate cannot be null")
    private Date startDate;
    @ApiModelProperty(name = "endDate", required = true)
    @NotNull(message = "endDate cannot be null")
    private Date endDate;
}
