package com.base.meta.mapper;

import com.base.meta.dto.testexecution.TestExecutionDto;
import com.base.meta.form.testexecution.CreateTestExecutionForm;
import com.base.meta.form.testexecution.UpdateTestExecutionForm;
import com.base.meta.model.TestExecution;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProjectMapper.class, AccountMapper.class, ProgramMapper.class, CategoryMapper.class})
public interface TestExecutionMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "detail", target = "detail")
    @Mapping(source = "planStartDate", target = "planStartDate")
    @Mapping(source = "planEndDate", target = "planEndDate")
    @BeanMapping(ignoreByDefault = true)
    TestExecution fromCreateTestExecutionFormToEntity(CreateTestExecutionForm createTestExecutionForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "status", target = "status", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "assignedDeveloper", target = "assignedDeveloper", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "program", target = "program", qualifiedByName = "fromEntityToAutoCompleteProgramDto")
    @Mapping(source = "detail", target = "detail")
    @Mapping(source = "planStartDate", target = "planStartDate")
    @Mapping(source = "planEndDate", target = "planEndDate")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestExecutionDto")
    TestExecutionDto fromEntityToTestExecutionDto(TestExecution testExecution);

    @IterableMapping(qualifiedByName = "fromEntityToTestExecutionDto", elementTargetType = TestExecutionDto.class)
    List<TestExecutionDto> fromEntityToTestExecutionDtoList(List<TestExecution> testExecutions);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "detail", target = "detail")
    @Mapping(source = "planStartDate", target = "planStartDate")
    @Mapping(source = "planEndDate", target = "planEndDate")
    @BeanMapping(ignoreByDefault = true)
    void updateTestExecutionFromToEntity(UpdateTestExecutionForm updateTestExecutionForm, @MappingTarget TestExecution testExecution);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestExecutionDtoAutoComplete")
    TestExecutionDto fromEntityToTestExecutionDtoAutoComplete(TestExecution testExecution);


}
