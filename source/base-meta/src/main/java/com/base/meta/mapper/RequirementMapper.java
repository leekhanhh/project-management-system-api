package com.base.meta.mapper;

import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.form.requirement.CreateRequirementForm;
import com.base.meta.form.requirement.UpdateRequirementForm;
import com.base.meta.model.Requirement;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProjectMapper.class, CategoryMapper.class})
public interface RequirementMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "acceptance", target = "acceptance", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "division", target = "division", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "name", target = "name", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "detailClassification", target = "detailClassification", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "project", target = "project", qualifiedByName = "fromEntityToProjectAutoCompleteDto")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToRequirementDto")
    RequirementDto fromEntityToRequirementDto(Requirement requirement);

    @IterableMapping(qualifiedByName = "fromEntityToRequirementDto", elementTargetType = RequirementDto.class)
    List<RequirementDto> fromEntityToRequirementDtoList(List<Requirement> requirements);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "acceptance", target = "acceptance", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "name", target = "name", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAutoCompleteRequirementDto")
    RequirementDto fromEntityToAutoCompleteRequirementDto(Requirement requirement);
}
