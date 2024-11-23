package com.base.meta.dto.testexecutionturn;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.testexecution.TestExecutionDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TestExecutionTurnDto extends ABasicAdminDto {
    private Long id;
    private Integer turnNumber;
    private Date startDate;
    private Date endDate;
    private Date actualStartDate;
    private Date actualEndDate;
    private AccountDto assignedDeveloper;
    private TestExecutionDto testExecution;
    private Integer testDefectCount;
    private Integer totalCasesCount;
    private Integer notExecutedCasesCount;
    private Integer waitingCasesCount;
    private Integer inProgressCasesCount;
    private Integer completedCasesCount;
    private String displayId;
}
