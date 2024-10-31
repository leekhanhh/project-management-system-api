package com.base.meta.mapper;

import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.dto.teststep.TestStepDto;
import com.base.meta.form.teststep.CreateTestStepForm;
import com.base.meta.form.teststep.UpdateTestStepForm;
import com.base.meta.model.Category;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.Requirement;
import com.base.meta.model.TestCase;
import com.base.meta.model.TestStep;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-30T12:32:44+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestStepMapperImpl implements TestStepMapper {

    @Override
    public TestStep fromCreateTestStepFormToEntity(CreateTestStepForm createTestStepForm) {
        if ( createTestStepForm == null ) {
            return null;
        }

        TestStep testStep = new TestStep();

        testStep.setData( createTestStepForm.getData() );
        testStep.setExpectedResult( createTestStepForm.getExpectedResult() );
        testStep.setAction( createTestStepForm.getAction() );
        testStep.setStepNumber( createTestStepForm.getStepNumber() );

        return testStep;
    }

    @Override
    public TestStepDto fromEntityToTestStepDto(TestStep testStep) {
        if ( testStep == null ) {
            return null;
        }

        TestStepDto testStepDto = new TestStepDto();

        testStepDto.setData( testStep.getData() );
        testStepDto.setExpectedResult( testStep.getExpectedResult() );
        testStepDto.setAction( testStep.getAction() );
        testStepDto.setId( testStep.getId() );
        testStepDto.setStepNumber( testStep.getStepNumber() );
        testStepDto.setTestCase( testCaseToTestCaseDto( testStep.getTestCase() ) );

        return testStepDto;
    }

    @Override
    public List<TestStepDto> fromEntityToTestStepDtoList(List<TestStep> testSteps) {
        if ( testSteps == null ) {
            return null;
        }

        List<TestStepDto> list = new ArrayList<TestStepDto>( testSteps.size() );
        for ( TestStep testStep : testSteps ) {
            list.add( fromEntityToTestStepDto( testStep ) );
        }

        return list;
    }

    @Override
    public void updateTestStepFromEntity(UpdateTestStepForm updateTestStepForm, TestStep testStep) {
        if ( updateTestStepForm == null ) {
            return;
        }

        if ( updateTestStepForm.getData() != null ) {
            testStep.setData( updateTestStepForm.getData() );
        }
        if ( updateTestStepForm.getExpectedResult() != null ) {
            testStep.setExpectedResult( updateTestStepForm.getExpectedResult() );
        }
        if ( updateTestStepForm.getAction() != null ) {
            testStep.setAction( updateTestStepForm.getAction() );
        }
    }

    @Override
    public TestStepDto fromEntityToAutoComplete(TestStep testStep) {
        if ( testStep == null ) {
            return null;
        }

        TestStepDto testStepDto = new TestStepDto();

        testStepDto.setId( testStep.getId() );
        testStepDto.setStepNumber( testStep.getStepNumber() );
        testStepDto.setData( testStep.getData() );
        testStepDto.setExpectedResult( testStep.getExpectedResult() );
        testStepDto.setFlag( testStep.getFlag() );
        if ( testStep.getModifiedDate() != null ) {
            testStepDto.setModifiedDate( LocalDateTime.ofInstant( testStep.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( testStep.getCreatedDate() != null ) {
            testStepDto.setCreatedDate( LocalDateTime.ofInstant( testStep.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testStepDto.setAction( testStep.getAction() );
        testStepDto.setTestCase( testCaseToTestCaseDto( testStep.getTestCase() ) );

        return testStepDto;
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

    protected TestCaseDto testCaseToTestCaseDto(TestCase testCase) {
        if ( testCase == null ) {
            return null;
        }

        TestCaseDto testCaseDto = new TestCaseDto();

        testCaseDto.setFlag( testCase.getFlag() );
        if ( testCase.getModifiedDate() != null ) {
            testCaseDto.setModifiedDate( LocalDateTime.ofInstant( testCase.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( testCase.getCreatedDate() != null ) {
            testCaseDto.setCreatedDate( LocalDateTime.ofInstant( testCase.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testCaseDto.setId( testCase.getId() );
        testCaseDto.setName( testCase.getName() );
        testCaseDto.setPrecondition( testCase.getPrecondition() );
        testCaseDto.setMenuPath( testCase.getMenuPath() );
        testCaseDto.setProgram( programToProgramDto( testCase.getProgram() ) );

        return testCaseDto;
    }
}
