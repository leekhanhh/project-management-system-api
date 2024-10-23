package com.base.meta.dto.testcaseupload;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.dto.project.ProjectDto;
import lombok.Data;

@Data
public class TestCaseUploadDto extends ABasicAdminDto {
    private Long id;
    private String testCasePrecondition;
    private String testCaseName;
    private String testCaseMenuPath;
    private String testStepsAction;
    private String testStepsData;
    private String testStepsExpectedResult;
    private String testStepsActualResult;
    private ProgramDto program;
}
