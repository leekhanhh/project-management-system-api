package com.base.meta.form.category;

import com.base.meta.validation.CategoryType;
import com.base.meta.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryForm {
    @CategoryType
    @ApiModelProperty(name = "categoryType", required = true,
            notes = "1: Account, 2:Project, 3:Requirement, " +
                    "4: Program, 5: Test Execution, 6: Test Suite Execution, " +
                    "7: Test Case Execution, 8: Test Step Execution, 9: Test Defect")
    @NotNull(message = "categoryKind cannot be null")
    private Integer categoryKind;
    @ApiModelProperty(name = "categoryName", required = true)
    @NotEmpty(message = "categoryName cannot be null")
    private String categoryName;
    @ApiModelProperty(name = "categoryCode")
    private String categoryCode;
    @ApiModelProperty(name = "categoryDescription", required = true)
    @NotEmpty(message = "categoryDescription cannot be null")
    private String categoryDescription;
    @ApiModelProperty(name = "categoryOrdering")
    private Integer categoryOrdering;
    @ApiModelProperty(name = "parentId")
    private Long parentId;
}
