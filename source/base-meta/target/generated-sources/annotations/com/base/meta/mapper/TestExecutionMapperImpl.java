package com.base.meta.mapper;

import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.dto.testexecution.TestExecutionDto;
import com.base.meta.form.testexecution.CreateTestExecutionForm;
import com.base.meta.form.testexecution.UpdateTestExecutionForm;
import com.base.meta.model.Category;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.Requirement;
import com.base.meta.model.TestExecution;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-06T17:26:55+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestExecutionMapperImpl implements TestExecutionMapper {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public TestExecution fromCreateTestExecutionFormToEntity(CreateTestExecutionForm createTestExecutionForm) {
        if ( createTestExecutionForm == null ) {
            return null;
        }

        TestExecution testExecution = new TestExecution();

        testExecution.setName( createTestExecutionForm.getName() );
        testExecution.setDetail( createTestExecutionForm.getDetail() );
        testExecution.setPlanStartDate( createTestExecutionForm.getPlanStartDate() );
        testExecution.setPlanEndDate( createTestExecutionForm.getPlanEndDate() );

        return testExecution;
    }

    @Override
    public TestExecutionDto fromEntityToTestExecutionDto(TestExecution testExecution) {
        if ( testExecution == null ) {
            return null;
        }

        TestExecutionDto testExecutionDto = new TestExecutionDto();

        testExecutionDto.setProgram( programToProgramDto( testExecution.getProgram() ) );
        testExecutionDto.setName( testExecution.getName() );
        testExecutionDto.setAssignedDeveloper( accountMapper.fromAccountToDto( testExecution.getAssignedDeveloper() ) );
        testExecutionDto.setId( testExecution.getId() );
        testExecutionDto.setDetail( testExecution.getDetail() );
        testExecutionDto.setCategory( categoryToCategoryDto( testExecution.getCategory() ) );
        testExecutionDto.setPlanStartDate( testExecution.getPlanStartDate() );
        testExecutionDto.setPlanEndDate( testExecution.getPlanEndDate() );
        testExecutionDto.setStatus( categoryToCategoryDto( testExecution.getStatus() ) );

        return testExecutionDto;
    }

    @Override
    public List<TestExecutionDto> fromEntityToTestExecutionDtoList(List<TestExecution> testExecutions) {
        if ( testExecutions == null ) {
            return null;
        }

        List<TestExecutionDto> list = new ArrayList<TestExecutionDto>( testExecutions.size() );
        for ( TestExecution testExecution : testExecutions ) {
            list.add( fromEntityToTestExecutionDto( testExecution ) );
        }

        return list;
    }

    @Override
    public void updateTestExecutionFromToEntity(UpdateTestExecutionForm updateTestExecutionForm, TestExecution testExecution) {
        if ( updateTestExecutionForm == null ) {
            return;
        }

        if ( updateTestExecutionForm.getName() != null ) {
            testExecution.setName( updateTestExecutionForm.getName() );
        }
        if ( updateTestExecutionForm.getDetail() != null ) {
            testExecution.setDetail( updateTestExecutionForm.getDetail() );
        }
        if ( updateTestExecutionForm.getPlanStartDate() != null ) {
            testExecution.setPlanStartDate( updateTestExecutionForm.getPlanStartDate() );
        }
        if ( updateTestExecutionForm.getPlanEndDate() != null ) {
            testExecution.setPlanEndDate( updateTestExecutionForm.getPlanEndDate() );
        }
    }

    @Override
    public TestExecutionDto fromEntityToTestExecutionDtoAutoComplete(TestExecution testExecution) {
        if ( testExecution == null ) {
            return null;
        }

        TestExecutionDto testExecutionDto = new TestExecutionDto();

        testExecutionDto.setName( testExecution.getName() );
        testExecutionDto.setId( testExecution.getId() );
        testExecutionDto.setCategory( categoryToCategoryDto( testExecution.getCategory() ) );

        return testExecutionDto;
    }

    protected CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setFlag( category.getFlag() );
        if ( category.getModifiedDate() != null ) {
            categoryDto.setModifiedDate( LocalDateTime.ofInstant( category.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( category.getCreatedDate() != null ) {
            categoryDto.setCreatedDate( LocalDateTime.ofInstant( category.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        categoryDto.setId( category.getId() );

        return categoryDto;
    }

    protected ProjectDto projectToProjectDto(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDto projectDto = new ProjectDto();

        projectDto.setFlag( project.getFlag() );
        if ( project.getModifiedDate() != null ) {
            projectDto.setModifiedDate( LocalDateTime.ofInstant( project.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( project.getCreatedDate() != null ) {
            projectDto.setCreatedDate( LocalDateTime.ofInstant( project.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        projectDto.setId( project.getId() );
        projectDto.setName( project.getName() );
        projectDto.setDescription( project.getDescription() );
        projectDto.setStartDate( project.getStartDate() );
        projectDto.setEndDate( project.getEndDate() );
        projectDto.setStatus( categoryToCategoryDto( project.getStatus() ) );

        return projectDto;
    }

    protected RequirementDto requirementToRequirementDto(Requirement requirement) {
        if ( requirement == null ) {
            return null;
        }

        RequirementDto requirementDto = new RequirementDto();

        requirementDto.setFlag( requirement.getFlag() );
        if ( requirement.getModifiedDate() != null ) {
            requirementDto.setModifiedDate( LocalDateTime.ofInstant( requirement.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( requirement.getCreatedDate() != null ) {
            requirementDto.setCreatedDate( LocalDateTime.ofInstant( requirement.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        requirementDto.setId( requirement.getId() );
        requirementDto.setName( categoryToCategoryDto( requirement.getName() ) );
        requirementDto.setDescription( requirement.getDescription() );
        requirementDto.setDevision( categoryToCategoryDto( requirement.getDevision() ) );
        requirementDto.setDetailClassification( categoryToCategoryDto( requirement.getDetailClassification() ) );
        requirementDto.setAcceptance( requirement.getAcceptance() );
        requirementDto.setProject( projectToProjectDto( requirement.getProject() ) );

        return requirementDto;
    }

    protected ProgramDto programToProgramDto(Program program) {
        if ( program == null ) {
            return null;
        }

        ProgramDto programDto = new ProgramDto();

        programDto.setFlag( program.getFlag() );
        if ( program.getModifiedDate() != null ) {
            programDto.setModifiedDate( LocalDateTime.ofInstant( program.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( program.getCreatedDate() != null ) {
            programDto.setCreatedDate( LocalDateTime.ofInstant( program.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        programDto.setId( program.getId() );
        programDto.setProject( projectToProjectDto( program.getProject() ) );
        programDto.setRequirement( requirementToRequirementDto( program.getRequirement() ) );
        programDto.setName( program.getName() );
        programDto.setDescription( program.getDescription() );
        programDto.setProgramType( categoryToCategoryDto( program.getProgramType() ) );
        programDto.setProgramCategory( program.getProgramCategory() );
        if ( program.getStartDate() != null ) {
            programDto.setStartDate( new SimpleDateFormat().format( program.getStartDate() ) );
        }
        if ( program.getEndDate() != null ) {
            programDto.setEndDate( new SimpleDateFormat().format( program.getEndDate() ) );
        }
        programDto.setProgramStatus( categoryToCategoryDto( program.getProgramStatus() ) );

        return programDto;
    }
}
