package com.base.meta.mapper;

import com.base.meta.dto.project.ProjectAutoCompleteDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.form.project.CreateProjectForm;
import com.base.meta.form.project.UpdateProjectForm;
import com.base.meta.model.Project;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-07T17:54:45+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Project fromCreateProjectFormToEntity(CreateProjectForm createProjectForm) {
        if ( createProjectForm == null ) {
            return null;
        }

        Project project = new Project();

        project.setEndDate( createProjectForm.getEndDate() );
        project.setDescription( createProjectForm.getDescription() );
        project.setName( createProjectForm.getName() );
        project.setStartDate( createProjectForm.getStartDate() );

        return project;
    }

    @Override
    public ProjectDto fromEntityToProjectDto(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDto projectDto = new ProjectDto();

        projectDto.setEndDate( project.getEndDate() );
        projectDto.setName( project.getName() );
        projectDto.setDescription( project.getDescription() );
        projectDto.setId( project.getId() );
        projectDto.setDisplayId( project.getDisplayId() );
        projectDto.setStartDate( project.getStartDate() );
        projectDto.setStatus( categoryMapper.fromEntityToAutoCompleteNameToDto( project.getStatus() ) );

        return projectDto;
    }

    @Override
    public List<ProjectDto> fromEntityToProjectDtoList(List<Project> projects) {
        if ( projects == null ) {
            return null;
        }

        List<ProjectDto> list = new ArrayList<ProjectDto>( projects.size() );
        for ( Project project : projects ) {
            list.add( fromEntityToProjectDto( project ) );
        }

        return list;
    }

    @Override
    public void updateProjectFromEntity(UpdateProjectForm updateProjectForm, Project project) {
        if ( updateProjectForm == null ) {
            return;
        }

        if ( updateProjectForm.getEndDate() != null ) {
            project.setEndDate( updateProjectForm.getEndDate() );
        }
        if ( updateProjectForm.getDescription() != null ) {
            project.setDescription( updateProjectForm.getDescription() );
        }
        if ( updateProjectForm.getName() != null ) {
            project.setName( updateProjectForm.getName() );
        }
        if ( updateProjectForm.getStartDate() != null ) {
            project.setStartDate( updateProjectForm.getStartDate() );
        }
    }

    @Override
    public ProjectAutoCompleteDto fromEntityToProjectAutoCompleteDto(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectAutoCompleteDto projectAutoCompleteDto = new ProjectAutoCompleteDto();

        projectAutoCompleteDto.setName( project.getName() );
        projectAutoCompleteDto.setId( project.getId() );
        projectAutoCompleteDto.setStatus( categoryMapper.fromEntityToAutoCompleteNameToDto( project.getStatus() ) );

        return projectAutoCompleteDto;
    }
}
