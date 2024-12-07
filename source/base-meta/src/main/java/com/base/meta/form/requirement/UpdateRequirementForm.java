package com.base.meta.form.requirement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateRequirementForm {
    @ApiModelProperty(value = "Requirement ID", required = true)
    @NotNull(message = "id cannot be null!")
    private Long id;
    @ApiModelProperty(value = "Requirement acceptance", required = true)
    @NotNull(message = "acceptance cannot be null!")
    private Long acceptanceId;
    @ApiModelProperty(value = "Requirement description", required = true)
    private String description;
    @ApiModelProperty(value = "Division", required = true)
    @NotNull(message = "division cannot be null!")
    private Long divisionId;
    @ApiModelProperty(value = "Name", required = true)
    @NotNull(message = "name cannot be null!")
    private Long nameId;
    @ApiModelProperty(value = "detail classification", required = true)
    private Long detailClassificationId;
}
