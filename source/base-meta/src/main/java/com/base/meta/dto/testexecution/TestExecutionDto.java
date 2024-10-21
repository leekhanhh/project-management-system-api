package com.base.meta.dto.testexecution;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.program.ProgramDto;
import lombok.Data;

import java.util.Date;

@Data
public class TestExecutionDto extends ABasicAdminDto {
    private Long id;
    private String name;
    private CategoryDto category;
    private CategoryDto status;
    private Date planStartDate;
    private Date planEndDate;
    private String detail;
    private AccountDto assignedDeveloper;
    private ProgramDto program;
    private Integer testExecutionTurnCount;
}
