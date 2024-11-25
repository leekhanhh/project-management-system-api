package com.base.meta.mapper;

import com.base.meta.dto.testsuitetestcaseralation.TestSuiteTestCaseRelationDto;
import com.base.meta.form.testsuitetestcaserelation.CreateTestSuiteTestCaseRelationForm;
import com.base.meta.model.TestCase;
import com.base.meta.model.TestSuite;
import com.base.meta.model.TestSuiteTestCaseRelation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestCase.class, TestSuite.class})
public interface TestSuiteTestCaseRelationMapper {
    @Mapping(source = "testSuite", target = "testSuite", qualifiedByName = "fromEntityToTestSuiteDtoAutoComplete")
    @Mapping(source = "testCase", target = "testCase", qualifiedByName = "fromEntityToTestCaseDtoAutoComplete")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    TestSuiteTestCaseRelationDto fromEntityToDto(TestSuiteTestCaseRelation testSuiteTestCaseRelation);

    @IterableMapping(qualifiedByName = "fromEntityToDto", elementTargetType = TestSuiteTestCaseRelationDto.class)
    List<TestSuiteTestCaseRelationDto> fromEntitiesToDtos(List<TestSuiteTestCaseRelation> testSuiteTestCaseRelations);
}
