package com.base.meta.mapper;

import com.base.meta.dto.testplantestsuiterelation.TestPlanTestSuiteRelationDto;
import com.base.meta.form.testplantestsuiterelation.CreateTestPlanTestSuiteRelationForm;
import com.base.meta.model.TestPlan;
import com.base.meta.model.TestPlanTestSuiteRelation;
import com.base.meta.model.TestSuite;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestPlan.class, TestSuite.class})
public interface TestPlanTestSuiteRelationMapper {
    @Mapping(source = "testPlanId", target = "testPlan.id")
    @Mapping(source = "testSuiteId", target = "testSuite.id")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateTestPlanTestSuiteRelationFormToEntity")
    TestPlanTestSuiteRelation fromCreateTestPlanTestSuiteRelationFormToEntity(CreateTestPlanTestSuiteRelationForm createTestPlanTestSuiteRelationForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "testPlan", target = "testPlan", qualifiedByName = "fromEntityToTestPlanDtoAutoComplete")
    @Mapping(source = "testSuite", target = "testSuite", qualifiedByName = "fromEntityToTestSuiteDtoAutoComplete")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestPlanTestSuiteRelationDto")
    TestPlanTestSuiteRelationDto fromEntityToTestPlanTestSuiteRelationDto(TestPlanTestSuiteRelation testPlanTestSuiteRelation);

    @IterableMapping(qualifiedByName = "fromEntityToTestPlanTestSuiteRelationDto", elementTargetType = TestPlanTestSuiteRelationDto.class)
    List<TestPlanTestSuiteRelationDto> fromDtoToTestPlanTestSuiteRelationDtoList(List<TestPlanTestSuiteRelation> testPlanTestSuiteRelations);
}
