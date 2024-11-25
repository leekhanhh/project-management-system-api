package com.base.meta.mapper;

import com.base.meta.dto.testexecutionturn.TestExecutionTurnDto;
import com.base.meta.form.testexecutionturn.CreateTestExecutionTurnForm;
import com.base.meta.form.testexecutionturn.UpdateTestExecutionTurnForm;
import com.base.meta.model.TestExecutionTurn;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, TestExecutionMapper.class})
public interface TestExecutionTurnMapper {
    @Mapping(source = "turnNumber", target = "turnNumber")
    @Mapping(source = "planStartDate", target = "planStartDate")
    @Mapping(source = "planEndDate", target = "planEndDate")
    @Mapping(source = "actualStartDate", target = "actualStartDate")
    @Mapping(source = "actualEndDate", target = "actualEndDate")
    @BeanMapping(ignoreByDefault = true)
    TestExecutionTurn fromCreateTestExecutionTurnFormToEntity(CreateTestExecutionTurnForm createTestExecutionTurnForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "turnNumber", target = "turnNumber")
    @Mapping(source = "planStartDate", target = "startDate")
    @Mapping(source = "planEndDate", target = "endDate")
    @Mapping(source = "actualStartDate", target = "actualStartDate")
    @Mapping(source = "actualEndDate", target = "actualEndDate")
    @Mapping(source = "assignedDeveloper", target = "assignedDeveloper", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "testExecution", target = "testExecution", qualifiedByName = "fromEntityToTestExecutionDtoAutoComplete")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    TestExecutionTurnDto fromEntityToDto(TestExecutionTurn testExecutionTurn);

    @IterableMapping(qualifiedByName = "fromEntityToDto", elementTargetType = TestExecutionTurnDto.class)
    List<TestExecutionTurnDto> fromEntitiesToDtos(List<TestExecutionTurn> testExecutionTurns);

    @Mapping(source = "turnNumber", target = "turnNumber")
    @Mapping(source = "planStartDate", target = "planStartDate")
    @Mapping(source = "planEndDate", target = "planEndDate")
    @Mapping(source = "actualStartDate", target = "actualStartDate")
    @Mapping(source = "actualEndDate", target = "actualEndDate")
    @BeanMapping(ignoreByDefault = true)
    void updateTestExecutionTurnFromToEntity(UpdateTestExecutionTurnForm updateTestExecutionTurnForm, @MappingTarget TestExecutionTurn testExecutionTurn);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "turnNumber", target = "turnNumber")
    @Mapping(source = "planStartDate", target = "startDate")
    @Mapping(source = "planEndDate", target = "endDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAutoCompleteTestExecutionTurnDto")
    TestExecutionTurnDto fromEntityToAutoCompleteTestExecutionTurnDto(TestExecutionTurn testExecutionTurn);
}
