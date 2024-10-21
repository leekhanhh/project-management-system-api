package com.base.meta.dto.program;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.dto.requirement.RequirementDto;
import lombok.Data;

@Data
public class ProgramDto extends ABasicAdminDto {
    private Long id;
    private ProjectDto project;
    private RequirementDto requirement;
    private String name;
    private String description;
    private CategoryDto programType;
    private String programCategory;
    private String startDate;
    private String endDate;
    private AccountDto account;
    private AccountDto developer;
    private AccountDto tester;
    private CategoryDto programStatus;
}
