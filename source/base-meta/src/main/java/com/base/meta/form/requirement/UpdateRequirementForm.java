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
    @ApiModelProperty(value = "Requirement acceptance id", required = true)
    @NotNull(message = "acceptance cannot be null!")
    private Long acceptanceId;
    @ApiModelProperty(value = "Requirement description", required = true)
    private String description;
    @ApiModelProperty(value = "Division id", required = true)
    @NotNull(message = "division cannot be null!")
    private Long divisionId;
    @ApiModelProperty(value = "Name Status id", required = true)
    @NotNull(message = "name status id cannot be null!")
    private Long nameStatusId;
    @ApiModelProperty(value = "detail classification id", required = true)
    private Long detailClassificationId;
}
