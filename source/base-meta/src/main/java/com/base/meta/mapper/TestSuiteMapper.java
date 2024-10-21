package com.base.meta.mapper;

import com.base.meta.dto.testsuite.TestSuiteDto;
import com.base.meta.form.testsuite.CreateTestSuiteForm;
import com.base.meta.form.testsuite.UpdateTestSuiteForm;
import com.base.meta.model.TestSuite;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestPlanMapper.class, AccountMapper.class})
public interface TestSuiteMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "testPlanId", target = "testPlan.id")
    @Mapping(source = "accountId", target = "account.id")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateTestSuiteFormToEntity")
    TestSuite fromCreateTestSuiteFormToEntity(CreateTestSuiteForm createTestSuiteForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "testPlan", target = "testPlan")
    @Mapping(source = "account", target = "account")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestSuiteDto")
    TestSuiteDto fromEntityToTestSuiteDto(TestSuite testSuite);

    @IterableMapping(qualifiedByName = "fromEntityToTestSuiteDto", elementTargetType = TestSuiteDto.class)
    @Named("fromEntityToTestSuiteDtoList")
    List<TestSuiteDto> fromEntityToTestSuiteDtoList(List<TestSuite> testSuiteList);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "accountId", target = "account.id")
    @BeanMapping(ignoreByDefault = true)
    void updateTestSuiteFromEntity(UpdateTestSuiteForm updateTestSuiteForm, @MappingTarget TestSuite testSuite);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestSuiteDtoAutoComplete")
    TestSuiteDto fromEntityToTestSuiteDtoAutoComplete(TestSuite testSuite);
}
