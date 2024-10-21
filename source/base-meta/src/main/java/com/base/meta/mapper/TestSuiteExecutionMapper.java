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
    @Mapping(source = "orderNumber", target = "orderNumber")
    @Mapping(source = "statusId", target = "status.id")
    @Mapping(source = "testSuiteId", target = "testSuite.id")
    @Mapping(source = "testExecutionTurnId", target = "testExecutionTurn.id")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateTestSuiteExecutionFormToEntity")
    TestSuiteExecution fromCreateTestSuiteExecutionFormToEntity(CreateTestSuiteExecutionForm createTestSuiteExecutionForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderNumber", target = "orderNumber")
    @Mapping(source = "status", target = "status", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "testSuite", target = "testSuite", qualifiedByName = "fromEntityToTestSuiteDtoAutoComplete")
    @Mapping(source = "testExecutionTurn", target = "testExecutionTurn", qualifiedByName = "fromEntityToAutoCompleteTestExecutionTurnDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestSuiteExecutionDto")
    TestSuiteExecutionDto fromEntityToTestSuiteExecutionDto(TestSuiteExecution testSuiteExecution);

    @IterableMapping(qualifiedByName = "fromEntityToTestSuiteExecutionDto", elementTargetType = TestSuiteExecutionDto.class)
    @Named("fromEntityToTestSuiteExecutionDtoList")
    List<TestSuiteExecutionDto> fromEntityToTestSuiteExecutionDtoList(List<TestSuiteExecution> testSuiteExecutionList);

    @Mapping(source = "orderNumber", target = "orderNumber")
    @Mapping(source = "statusId", target = "status.id")
    @Mapping(source = "flag", target = "flag")
    @BeanMapping(ignoreByDefault = true)
    void updateTestSuiteExecutionFromEntity(UpdateTestSuiteExecutionForm updateTestSuiteExecutionForm, @MappingTarget TestSuiteExecution testSuiteExecution);
}
