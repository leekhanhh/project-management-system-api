package com.base.meta.mapper;

import com.base.meta.dto.testplan.TestPlanDto;
import com.base.meta.form.testplan.CreateTestPlanForm;
import com.base.meta.form.testplan.UpdateTestPlanForm;
import com.base.meta.model.TestPlan;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProjectMapper.class, ProgramMapper.class})
public interface TestPlanMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @BeanMapping(ignoreByDefault = true)
    TestPlan fromCreateTestPlanFormToEntity(CreateTestPlanForm createTestPlanForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "program", target = "program", qualifiedByName = "fromEntityToAutoCompleteProgramDto")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestPlanDto")
    TestPlanDto fromEntityToTestPlanDto(TestPlan testPlan);

    @IterableMapping(qualifiedByName = "fromEntityToTestPlanDto", elementTargetType = TestPlanDto.class)
    List<TestPlanDto> fromEntityToTestPlanDtoList(List<TestPlan> testPlans);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @BeanMapping(ignoreByDefault = true)
    void updateTestPlanFromEntity(UpdateTestPlanForm updateTestPlanForm, @MappingTarget TestPlan testPlan);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestPlanDtoAutoComplete")
    TestPlanDto fromEntityToTestPlanDtoAutoComplete(TestPlan testPlan);

}
