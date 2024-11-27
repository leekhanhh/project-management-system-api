package com.base.meta.mapper;

import com.base.meta.dto.testsuiteexecution.TestSuiteExecutionDto;
import com.base.meta.form.testsuiteexecution.CreateTestSuiteExecutionForm;
import com.base.meta.form.testsuiteexecution.UpdateTestSuiteExecutionForm;
import com.base.meta.model.TestSuiteExecution;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestPlanMapper.class, AccountMapper.class})
public interface TestSuiteExecutionMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderNumber", target = "orderNumber")
    @Mapping(source = "status", target = "status", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "testSuite", target = "testSuite", qualifiedByName = "fromEntityToTestSuiteDtoAutoComplete")
    @Mapping(source = "testExecutionTurn", target = "testExecutionTurn", qualifiedByName = "fromEntityToAutoCompleteTestExecutionTurnDto")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestSuiteExecutionDto")
    TestSuiteExecutionDto fromEntityToTestSuiteExecutionDto(TestSuiteExecution testSuiteExecution);

    @IterableMapping(qualifiedByName = "fromEntityToTestSuiteExecutionDto", elementTargetType = TestSuiteExecutionDto.class)
    @Named("fromEntityToTestSuiteExecutionDtoList")
    List<TestSuiteExecutionDto> fromEntityToTestSuiteExecutionDtoList(List<TestSuiteExecution> testSuiteExecutionList);
}
