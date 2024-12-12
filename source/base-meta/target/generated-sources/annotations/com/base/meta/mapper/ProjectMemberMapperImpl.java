package com.base.meta.mapper;

import com.base.meta.dto.projectmember.ProjectMemberDto;
import com.base.meta.form.projectmember.CreateProjectMemberForm;
import com.base.meta.form.projectmember.UpdateProjectMemberForm;
import com.base.meta.model.ProjectMember;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-09T22:05:25+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ProjectMemberMapperImpl implements ProjectMemberMapper {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ProjectMember fromCreateProjectMemberFormToEntity(CreateProjectMemberForm createProjectMemberForm) {
        if ( createProjectMemberForm == null ) {
            return null;
        }

        ProjectMember projectMember = new ProjectMember();

        projectMember.setOnBoardedDate( createProjectMemberForm.getOnBoardedDate() );
        projectMember.setOffBoardedDate( createProjectMemberForm.getOffBoardedDate() );

        return projectMember;
    }

    @Override
    public ProjectMemberDto fromEntityToProjectMemberDto(ProjectMember projectMember) {
        if ( projectMember == null ) {
            return null;
        }

        ProjectMemberDto projectMemberDto = new ProjectMemberDto();

        if ( projectMember.getOnBoardedDate() != null ) {
            projectMemberDto.setOnBoardedDate( new SimpleDateFormat().format( projectMember.getOnBoardedDate() ) );
        }
        if ( projectMember.getOffBoardedDate() != null ) {
            projectMemberDto.setOffBoardedDate( new SimpleDateFormat().format( projectMember.getOffBoardedDate() ) );
        }
        projectMemberDto.setProject( projectMapper.fromEntityToProjectDto( projectMember.getProject() ) );
        projectMemberDto.setId( projectMember.getId() );
        projectMemberDto.setAccount( accountMapper.fromAccountToDto( projectMember.getAccount() ) );

        return projectMemberDto;
    }

    @Override
    public List<ProjectMemberDto> fromEntityToProjectMemberDtoList(List<ProjectMember> projectMembers) {
        if ( projectMembers == null ) {
            return null;
        }

        List<ProjectMemberDto> list = new ArrayList<ProjectMemberDto>( projectMembers.size() );
        for ( ProjectMember projectMember : projectMembers ) {
            list.add( fromEntityToProjectMemberDto( projectMember ) );
        }

        return list;
    }

    @Override
    public void updateProjectMemberFromEntity(UpdateProjectMemberForm updateProjectMemberForm, ProjectMember projectMember) {
        if ( updateProjectMemberForm == null ) {
            return;
        }

        try {
            if ( updateProjectMemberForm.getOnBoardedDate() != null ) {
                projectMember.setOnBoardedDate( new SimpleDateFormat().parse( updateProjectMemberForm.getOnBoardedDate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( updateProjectMemberForm.getOffBoardedDate() != null ) {
                projectMember.setOffBoardedDate( new SimpleDateFormat().parse( updateProjectMemberForm.getOffBoardedDate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
    }
}
