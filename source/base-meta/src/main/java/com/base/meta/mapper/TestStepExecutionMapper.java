package com.base.meta.mapper;

import com.base.meta.dto.teststepexecution.TestStepExecutionDto;
import com.base.meta.form.teststepexecution.UpdateTestStepExecutionForm;
import com.base.meta.model.TestStepExecution;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestStepMapper.class, CategoryMapper.class, TestCaseExecutionMapper.class})
public interface TestStepExecutionMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "testStep", target = "testStep", qualifiedByName = "fromEntityToAutoCompleteTestStepDto")
    @Mapping(source = "testCaseExecution", target = "testCaseExecution", qualifiedByName = "fromEntityToTestCaseExecutionDto")
    @Mapping(source = "status", target = "status", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "isDefected", target = "isDefected")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    TestStepExecutionDto fromEntityToDto(TestStepExecution testStepExecution);

    @IterableMapping(elementTargetType = TestStepExecutionDto.class, qualifiedByName = "fromEntityToDto")
    List<TestStepExecutionDto> fromEntityListToDtoList(List<TestStepExecution> testStepExecutionList);
}
