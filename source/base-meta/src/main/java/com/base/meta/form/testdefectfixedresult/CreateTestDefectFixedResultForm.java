package com.base.meta.form.testdefectfixedresult;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateTestDefectFixedResultForm {
    @ApiModelProperty(value = "Test defect id", required = true)
    @NotNull(message = "Test defect id is required")
    private Long testDefectId;
    @ApiModelProperty(value = "actionStartDate")
    private Date actionStartDate;
    @ApiModelProperty(value = "description", required = true)
    @NotEmpty(message = "description is required")
    private String description;
    @ApiModelProperty(value = "remark")
    private String remark;
}
