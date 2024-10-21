package com.base.meta.mapper;

import com.base.meta.dto.testdefect.TestDefectDto;
import com.base.meta.form.testdefect.CreateTestDefectForm;
import com.base.meta.form.testdefect.UpdateTestDefectForm;
import com.base.meta.model.TestDefect;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class, TestStepExecutionMapper.class, AccountMapper.class})
public interface TestDefectMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "severity", target = "severity")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    TestDefect fromCreateTestDefectFormToEntity(CreateTestDefectForm createTestDefectForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "severity", target = "severity")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "assignedDeveloper", target = "assignedDeveloper", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "testStepExecution", target = "testStepExecution", qualifiedByName = "fromEntityToDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    TestDefectDto fromEntityToDto(TestDefect testDefect);

    @IterableMapping(elementTargetType = TestDefectDto.class, qualifiedByName = "fromEntityToDto")
    List<TestDefectDto> fromEntityListToDtoList(List<TestDefect> testDefectList);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoAutoComplete")
    TestDefectDto fromEntityToDtoAutoComplete(TestDefect testDefect);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "severity", target = "severity")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "sendNotification", target = "sendNotification")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdateTestDefectFormToEntity(UpdateTestDefectForm updateTestDefectForm, @MappingTarget TestDefect testDefect);
}
