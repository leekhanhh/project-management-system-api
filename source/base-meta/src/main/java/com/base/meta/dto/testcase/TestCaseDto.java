package com.base.meta.dto.testcase;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.program.ProgramDto;
import lombok.Data;

@Data
public class TestCaseDto extends ABasicAdminDto {
    private Long id;
    private String name;
    private String precondition;
    private String menuPath;
    private ProgramDto program;
}
