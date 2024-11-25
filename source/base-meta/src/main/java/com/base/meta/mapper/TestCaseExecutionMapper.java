package com.base.meta.mapper;

import com.base.meta.dto.testcaseexecution.TestCaseExecutionDto;
import com.base.meta.form.testcaseexecution.CreateTestCaseExecutionForm;
import com.base.meta.form.testcaseexecution.UpdateTestCaseExecutionForm;
import com.base.meta.model.TestCaseExecution;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestCaseMapper.class, TestSuiteMapper.class, CategoryMapper.class, TestExecutionTurnMapper.class})
public interface TestCaseExecutionMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "testExecutionTurn", target = "testExecutionTurn", qualifiedByName = "fromEntityToAutoCompleteTestExecutionTurnDto")
    @Mapping(source = "testCase", target = "testCase", qualifiedByName = "fromEntityToTestCaseDtoAutoComplete")
    @Mapping(source = "testSuite", target = "testSuite", qualifiedByName = "fromEntityToTestSuiteDtoAutoComplete")
    @Mapping(source = "status", target = "status", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "testExecutionTypeCode", target = "testExecutionTypeCode", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestCaseExecutionDto")
    TestCaseExecutionDto fromEntityToTestCaseExecutionDto(TestCaseExecution testCaseExecution);

    @IterableMapping(qualifiedByName = "fromEntityToTestCaseExecutionDto", elementTargetType = TestCaseExecutionDto.class)
    List<TestCaseExecutionDto> fromEntityToTestCaseExecutionDtoList(List<TestCaseExecution> testCaseExecutions);

    @BeanMapping(ignoreByDefault = true)
    void fromUpdateTestCaseExecutionFormToEntity(UpdateTestCaseExecutionForm updateTestCaseExecutionForm, @MappingTarget TestCaseExecution testCaseExecution);
}
