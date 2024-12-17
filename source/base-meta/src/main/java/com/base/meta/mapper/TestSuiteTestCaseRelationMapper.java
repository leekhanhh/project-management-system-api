package com.base.meta.mapper;

import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.dto.testsuitetestcaseralation.TestSuiteTestCaseRelationDto;
import com.base.meta.model.TestSuiteTestCaseRelation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestCaseMapper.class, TestSuiteMapper.class})
public interface TestSuiteTestCaseRelationMapper {
    @Mapping(source = "testSuite", target = "testSuite", qualifiedByName = "fromEntityToTestSuiteDtoAutoComplete")
    @Mapping(source = "testCase", target = "testCase", qualifiedByName = "fromEntityToTestCaseDtoAutoComplete")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    TestSuiteTestCaseRelationDto fromEntityToDto(TestSuiteTestCaseRelation testSuiteTestCaseRelation);

    @IterableMapping(qualifiedByName = "fromEntityToDto", elementTargetType = TestSuiteTestCaseRelationDto.class)
    List<TestSuiteTestCaseRelationDto> fromEntitiesToDtos(List<TestSuiteTestCaseRelation> testSuiteTestCaseRelations);

    @Mapping(source = "testCase.name", target = "name")
    @Mapping(source = "testCase.precondition", target = "precondition")
    @Mapping(source = "testCase.displayId", target = "displayId")
    @Mapping(source = "testCase.id", target = "id")
    @Mapping(source = "testCase.menuPath", target = "menuPath")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromShortenedEntityToDto")
    TestCaseDto fromShortenedEntityToDto(TestSuiteTestCaseRelation testSuiteTestCaseRelation);

    @IterableMapping(qualifiedByName = "fromShortenedEntityToDto", elementTargetType = TestCaseDto.class)
    List<TestCaseDto> fromShortenedEntitiesToDtos(List<TestSuiteTestCaseRelation> testSuiteTestCaseRelations);
}
