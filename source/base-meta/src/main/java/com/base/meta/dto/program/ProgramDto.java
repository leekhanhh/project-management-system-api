package com.base.meta.dto.program;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.dto.requirement.RequirementDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProgramDto extends ABasicAdminDto {
    private Long id;
    @ApiModelProperty(name = "project")
    private ProjectDto project;
    @ApiModelProperty(name = "requirement")
    private RequirementDto requirement;
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "programType")
    private CategoryDto programType;
    @ApiModelProperty(name = "programCategory")
    private String programCategory;
    @ApiModelProperty(name = "startDate")
    private String startDate;
    @ApiModelProperty(name = "endDate")
    private String endDate;
    @ApiModelProperty(name = "project owner account")
    private AccountDto owner;
    @ApiModelProperty(name = "developer account")
    private AccountDto developer;
    @ApiModelProperty(name = "tester account")
    private AccountDto tester;
    @ApiModelProperty(name = "programStatus")
    private CategoryDto programStatus;
    @ApiModelProperty(name = "displayId")
    private String displayId;
}
