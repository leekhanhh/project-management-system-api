package com.base.meta.form.program;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateProgramForm {
    @ApiModelProperty(name = "project id", required = true)
    @NotNull(message = "project id cannot be null")
    private Long projectId;
    @ApiModelProperty(name = "requirement id", required = true)
    @NotNull(message = "requirement id cannot be null")
    private Long requirementId;
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "program's name cannot be null")
    private String name;
    @ApiModelProperty(name = "description", required = true)
    private String description;
    @ApiModelProperty(name = "program type", required = true)
    @NotNull(message = "program type cannot be null")
    private Long programTypeId;
    @ApiModelProperty(name = "program category", required = true)
    private String programCategory;
    @ApiModelProperty(name = "start date", required = true)
    private Date startDate;
    @ApiModelProperty(name = "end date", required = true)
    private Date endDate;
    @ApiModelProperty(name = "program owner", required = true)
    private Long programOwnerId;
    @ApiModelProperty(name = "developer", required = true)
    private Long developerId;
    @ApiModelProperty(name = "tester", required = true)
    private Long testerId;
    @ApiModelProperty(name = "status", required = true)
    @NotNull(message = "status cannot be null")
    private Long statusId;
}
