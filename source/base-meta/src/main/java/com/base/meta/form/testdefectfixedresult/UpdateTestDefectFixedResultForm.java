package com.base.meta.form.testdefectfixedresult;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateTestDefectFixedResultForm {
    @NotNull(message = "id is required")
    @ApiModelProperty(required = true)
    private Long id;
    @ApiModelProperty(value = "actionStartDate")
    private Date actionStartDate;
    @ApiModelProperty(value = "description", required = true)
    @NotEmpty(message = "description is required")
    private String description;
    @ApiModelProperty(value = "remark")
    private String remark;
}
