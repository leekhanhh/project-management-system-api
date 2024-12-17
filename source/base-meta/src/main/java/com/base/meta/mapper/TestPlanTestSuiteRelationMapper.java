package com.base.meta.mapper;

import com.base.meta.dto.testplantestsuiterelation.TestPlanTestSuiteRelationDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import com.base.meta.form.testplantestsuiterelation.CreateTestPlanTestSuiteRelationForm;
import com.base.meta.model.TestPlan;
import com.base.meta.model.TestPlanTestSuiteRelation;
import com.base.meta.model.TestSuite;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestPlanMapper.class, TestSuiteMapper.class, AccountMapper.class, ProgramMapper.class})
public interface TestPlanTestSuiteRelationMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "testPlan", target = "testPlan")
    @Mapping(source = "testSuite", target = "testSuite")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestPlanTestSuiteRelationDto")
    TestPlanTestSuiteRelationDto fromEntityToTestPlanTestSuiteRelationDto(TestPlanTestSuiteRelation testPlanTestSuiteRelation);

    @IterableMapping(qualifiedByName = "fromEntityToTestPlanTestSuiteRelationDto", elementTargetType = TestPlanTestSuiteRelationDto.class)
    List<TestPlanTestSuiteRelationDto> fromDtoToTestPlanTestSuiteRelationDtoList(List<TestPlanTestSuiteRelation> testPlanTestSuiteRelations);

    @Mapping(source = "testSuite.id", target = "id")
    @Mapping(source = "testSuite.name", target = "name")
    @Mapping(source = "testSuite.description", target = "description")
    @Mapping(source = "testSuite.displayId", target = "displayId")
    @Mapping(source = "testSuite.account", target = "account", qualifiedByName = "fromEntityToAutoCompleteAccountNameDto")
    @Mapping(source = "testSuite.program", target = "program", qualifiedByName = "fromEntityToAutoCompleteProgramNameDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromShortenedEntityToTestSuiteDto")
    TestSuiteDto fromShortenedEntityToTestPlanTestSuiteRelationDto(TestPlanTestSuiteRelation testPlanTestSuiteRelation);

    @IterableMapping(qualifiedByName = "fromShortenedEntityToTestSuiteDto", elementTargetType = TestSuiteDto.class)
    List<TestSuiteDto> fromShortenedEntitiesToTestPlanTestSuiteRelationDtos(List<TestPlanTestSuiteRelation> testPlanTestSuiteRelations);
}
