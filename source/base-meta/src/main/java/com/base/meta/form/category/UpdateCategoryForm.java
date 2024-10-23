package com.base.meta.form.category;

import com.base.meta.validation.CategoryType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
public class UpdateCategoryForm {
    @ApiModelProperty(value = "Category id", required = true)
    @NotNull(message = "Category id is required")
    private Long id;
    @ApiModelProperty(name = "categoryType", required = true, notes = "1: ")
    @NotNull(message = "categoryKind cannot be null")
    private Long categoryKind;
    @ApiModelProperty(name = "categoryName", required = true)
    @NotEmpty(message = "categoryName cannot be null")
    private String categoryName;
    @ApiModelProperty(name = "categoryCode", required = true)
    @NotEmpty(message = "categoryCode cannot be null")
    private String categoryCode;
    @ApiModelProperty(name = "categoryDescription", required = true)
    @NotEmpty(message = "categoryDescription cannot be null")
    private String categoryDescription;
    @ApiModelProperty(name = "categoryOrdering")
    private Integer categoryOrdering;
    @ApiModelProperty(name = "system flag")

    private Integer flag;
}
