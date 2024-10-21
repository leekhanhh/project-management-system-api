package com.base.meta.form.requirement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateRequirementForm {
    @ApiModelProperty(value = "Requirement ID", required = true)
    @NotEmpty(message = "id cannot be null!")
    private Long id;
    @ApiModelProperty(value = "Requirement name", required = true)
    @NotEmpty(message = "name cannot be null!")
    private String name;
    @ApiModelProperty(value = "Requirement description", required = true)
    private String description;
    @ApiModelProperty(value = "Devision", required = true)
    @NotEmpty(message = "devision cannot be null!")
    private Long devisionId;
    @ApiModelProperty(value = "Acceptance", required = true)
    private Long acceptanceId;
    @ApiModelProperty(value = "detail classification", required = true)
    private Long detailClassificationId;
}
