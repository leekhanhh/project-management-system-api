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
    private String acceptance;
    @ApiModelProperty(value = "Requirement description", required = true)
    private String description;
    @ApiModelProperty(value = "Devision", required = true)
    @NotNull(message = "devision cannot be null!")
    private Long devisionId;
    @ApiModelProperty(value = "Name", required = true)
    @NotNull(message = "name cannot be null!")
    private Long nameId;
    @ApiModelProperty(value = "detail classification", required = true)
    private Long detailClassificationId;
}
