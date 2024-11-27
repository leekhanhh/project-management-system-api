package com.base.meta.mapper;

import com.base.meta.dto.project.ProjectAutoCompleteDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.form.project.CreateProjectForm;
import com.base.meta.form.project.UpdateProjectForm;
import com.base.meta.model.Project;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class})
public interface ProjectMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @BeanMapping(ignoreByDefault = true)
    Project fromCreateProjectFormToEntity(CreateProjectForm createProjectForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProjectDto")
    ProjectDto fromEntityToProjectDto(Project project);

    @IterableMapping(elementTargetType = ProjectDto.class, qualifiedByName = "fromEntityToProjectDto")
    List<ProjectDto> fromEntityToProjectDtoList(List<Project> projects);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @BeanMapping(ignoreByDefault = true)
    void updateProjectFromEntity(UpdateProjectForm updateProjectForm, @MappingTarget Project project);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProjectAutoCompleteDto")
    ProjectAutoCompleteDto fromEntityToProjectAutoCompleteDto(Project project);

}
