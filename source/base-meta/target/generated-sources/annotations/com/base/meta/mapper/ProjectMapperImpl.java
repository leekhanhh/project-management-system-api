package com.base.meta.mapper;

import com.base.meta.dto.project.ProjectDto;
import com.base.meta.form.project.CreateProjectForm;
import com.base.meta.form.project.UpdateProjectForm;
import com.base.meta.model.Project;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-07T16:23:25+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project fromCreateProjectFormToEntity(CreateProjectForm createProjectForm) {
        if ( createProjectForm == null ) {
            return null;
        }

        Project project = new Project();

        try {
            if ( createProjectForm.getEndDate() != null ) {
                project.setEndDate( new SimpleDateFormat().parse( createProjectForm.getEndDate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        project.setName( createProjectForm.getName() );
        project.setDescription( createProjectForm.getDescription() );
        try {
            if ( createProjectForm.getStartDate() != null ) {
                project.setStartDate( new SimpleDateFormat().parse( createProjectForm.getStartDate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        if ( createProjectForm.getStatus() != null ) {
            project.setStatus( createProjectForm.getStatus() );
        }

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
        projectDto.setStartDate( project.getStartDate() );
        projectDto.setStatus( project.getStatus() );

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

        try {
            if ( updateProjectForm.getEndDate() != null ) {
                project.setEndDate( new SimpleDateFormat().parse( updateProjectForm.getEndDate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        if ( updateProjectForm.getName() != null ) {
            project.setName( updateProjectForm.getName() );
        }
        if ( updateProjectForm.getDescription() != null ) {
            project.setDescription( updateProjectForm.getDescription() );
        }
        try {
            if ( updateProjectForm.getStartDate() != null ) {
                project.setStartDate( new SimpleDateFormat().parse( updateProjectForm.getStartDate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        if ( updateProjectForm.getStatus() != null ) {
            project.setStatus( updateProjectForm.getStatus() );
        }
    }
}
