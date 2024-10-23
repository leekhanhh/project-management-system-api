package com.base.meta.mapper;

import com.base.meta.dto.program.ProgramAutoCompleteDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.form.program.CreateProgramForm;
import com.base.meta.form.program.UpdateProgramForm;
import com.base.meta.model.Program;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, CategoryMapper.class, ProjectMapper.class, RequirementMapper.class})
public interface ProgramMapper {
    @Mapping(source = "programCategory", target = "programCategory")
    @Mapping(source="name", target="name")
    @Mapping(source="description", target="description")
    @Mapping(source="startDate", target="startDate")
    @Mapping(source="endDate", target="endDate")
    @BeanMapping(ignoreByDefault = true)
    Program fromCreateProgramFormToEntity(CreateProgramForm createProgramForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "project", target = "project")
    @Mapping(source = "requirement", target = "requirement")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(target = "owner", source = "programOwner", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(target = "developer", source = "assignedDeveloper", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(target = "tester", source = "assignedTester", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "programStatus", target = "programStatus", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "programType", target = "programType", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "programCategory", target = "programCategory")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromDtoToEntity")
    ProgramDto fromDtoToEntity(Program program);

    @IterableMapping(qualifiedByName = "fromDtoToEntity", elementTargetType = ProgramDto.class)
    @Named("fromDtoListToEntityList")
    List<ProgramDto> fromEntityToProgramDtoList(List<Program> programs);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "programCategory", target = "programCategory")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromUpdateFormToEntity")
    void fromUpdateFormToEntity(UpdateProgramForm updateProgramForm, @MappingTarget Program program);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "project", target = "project", qualifiedByName = "fromEntityToAutoCompleteProjectDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAutoCompleteProgramDto")
    ProgramDto fromEntityToAutoCompleteProgramDto(Program program);
}
