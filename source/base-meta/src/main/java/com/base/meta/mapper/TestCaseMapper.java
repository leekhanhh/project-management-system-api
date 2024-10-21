package com.base.meta.mapper;

import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.form.testcase.CreateTestCaseForm;
import com.base.meta.form.testcase.UpdateTestCaseForm;
import com.base.meta.model.TestCase;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProgramMapper.class})
public interface TestCaseMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "precondition", target = "precondition")
    @Mapping(source = "menuPath", target = "menuPath")
    @Mapping(source = "programId", target = "program.id")
    @BeanMapping(ignoreByDefault = true)
    TestCase fromCreateTestCaseFormToEntity(CreateTestCaseForm createTestCaseForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "precondition", target = "precondition")
    @Mapping(source = "menuPath", target = "menuPath")
    @Mapping(source = "program", target = "program", qualifiedByName = "fromEntityToAutoCompleteProgramDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestCaseDto")
    TestCaseDto fromEntityToTestCaseDto(TestCase testCase);

    @IterableMapping(qualifiedByName = "fromEntityToTestCaseDto", elementTargetType = TestCaseDto.class)
    List<TestCaseDto> fromEntityToTestCaseDtoList(List<TestCase> testCases);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "precondition", source = "precondition")
    @Mapping(target = "menuPath", source = "menuPath")
    @BeanMapping(ignoreByDefault = true)
    void fromUpdateTestCaseFormToEntity(UpdateTestCaseForm updateTestCaseForm, @MappingTarget TestCase testCase);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "precondition", target = "precondition")
    @Mapping(source = "menuPath", target = "menuPath")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestCaseDtoAutoComplete")
    TestCaseDto fromEntityToTestCaseDtoAutoComplete(TestCase testCase);
}
