package com.base.meta.mapper;

import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.group.GroupDto;
import com.base.meta.dto.permission.PermissionDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.dto.testcaseexecution.TestCaseExecutionDto;
import com.base.meta.dto.testexecution.TestExecutionDto;
import com.base.meta.dto.testexecutionturn.TestExecutionTurnDto;
import com.base.meta.form.testcaseexecution.UpdateTestCaseExecutionForm;
import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.Group;
import com.base.meta.model.Permission;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.Requirement;
import com.base.meta.model.TestCaseExecution;
import com.base.meta.model.TestExecution;
import com.base.meta.model.TestExecutionTurn;
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
    date = "2024-10-26T14:24:02+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestCaseExecutionMapperImpl implements TestCaseExecutionMapper {

    @Autowired
    private TestCaseMapper testCaseMapper;
    @Autowired
    private TestSuiteMapper testSuiteMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public TestCaseExecutionDto fromEntityToTestCaseExecutionDto(TestCaseExecution testCaseExecution) {
        if ( testCaseExecution == null ) {
            return null;
        }

        TestCaseExecutionDto testCaseExecutionDto = new TestCaseExecutionDto();

        testCaseExecutionDto.setTestExecutionTypeCode( categoryMapper.fromEntityToAutoCompleteNameToDto( testCaseExecution.getTestExecutionTypeCode() ) );
        testCaseExecutionDto.setTestSuite( testSuiteMapper.fromEntityToTestSuiteDtoAutoComplete( testCaseExecution.getTestSuite() ) );
        testCaseExecutionDto.setTestExecutionTurn( testExecutionTurnToTestExecutionTurnDto( testCaseExecution.getTestExecutionTurn() ) );
        testCaseExecutionDto.setId( testCaseExecution.getId() );
        testCaseExecutionDto.setTestCase( testCaseMapper.fromEntityToTestCaseDtoAutoComplete( testCaseExecution.getTestCase() ) );
        testCaseExecutionDto.setStatus( categoryMapper.fromEntityToAutoCompleteNameToDto( testCaseExecution.getStatus() ) );

        return testCaseExecutionDto;
    }

    @Override
    public List<TestCaseExecutionDto> fromEntityToTestCaseExecutionDtoList(List<TestCaseExecution> testCaseExecutions) {
        if ( testCaseExecutions == null ) {
            return null;
        }

        List<TestCaseExecutionDto> list = new ArrayList<TestCaseExecutionDto>( testCaseExecutions.size() );
        for ( TestCaseExecution testCaseExecution : testCaseExecutions ) {
            list.add( fromEntityToTestCaseExecutionDto( testCaseExecution ) );
        }

        return list;
    }

    @Override
    public void fromUpdateTestCaseExecutionFormToEntity(UpdateTestCaseExecutionForm updateTestCaseExecutionForm, TestCaseExecution testCaseExecution) {
        if ( updateTestCaseExecutionForm == null ) {
            return;
        }
    }

    protected PermissionDto permissionToPermissionDto(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionDto permissionDto = new PermissionDto();

        permissionDto.setFlag( permission.getFlag() );
        if ( permission.getModifiedDate() != null ) {
            permissionDto.setModifiedDate( LocalDateTime.ofInstant( permission.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( permission.getCreatedDate() != null ) {
            permissionDto.setCreatedDate( LocalDateTime.ofInstant( permission.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        permissionDto.setId( permission.getId() );
        permissionDto.setName( permission.getName() );
        permissionDto.setAction( permission.getAction() );
        permissionDto.setShowMenu( permission.getShowMenu() );
        permissionDto.setDescription( permission.getDescription() );
        permissionDto.setNameGroup( permission.getNameGroup() );
        permissionDto.setPermissionCode( permission.getPermissionCode() );
        permissionDto.setKind( permission.getKind() );

        return permissionDto;
    }

    protected List<PermissionDto> permissionListToPermissionDtoList(List<Permission> list) {
        if ( list == null ) {
            return null;
        }

        List<PermissionDto> list1 = new ArrayList<PermissionDto>( list.size() );
        for ( Permission permission : list ) {
            list1.add( permissionToPermissionDto( permission ) );
        }

        return list1;
    }

    protected GroupDto groupToGroupDto(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        groupDto.setFlag( group.getFlag() );
        if ( group.getModifiedDate() != null ) {
            groupDto.setModifiedDate( LocalDateTime.ofInstant( group.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( group.getCreatedDate() != null ) {
            groupDto.setCreatedDate( LocalDateTime.ofInstant( group.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        groupDto.setId( group.getId() );
        groupDto.setName( group.getName() );
        groupDto.setDescription( group.getDescription() );
        groupDto.setKind( group.getKind() );
        groupDto.setIsSystemRole( group.getIsSystemRole() );
        groupDto.setPermissions( permissionListToPermissionDtoList( group.getPermissions() ) );

        return groupDto;
    }

    protected AccountDto accountToAccountDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setFlag( account.getFlag() );
        if ( account.getModifiedDate() != null ) {
            accountDto.setModifiedDate( LocalDateTime.ofInstant( account.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( account.getCreatedDate() != null ) {
            accountDto.setCreatedDate( LocalDateTime.ofInstant( account.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        accountDto.setId( account.getId() );
        accountDto.setKind( account.getKind() );
        accountDto.setUsername( account.getUsername() );
        accountDto.setPhone( account.getPhone() );
        accountDto.setEmail( account.getEmail() );
        accountDto.setFullName( account.getFullName() );
        accountDto.setGroup( groupToGroupDto( account.getGroup() ) );
        accountDto.setLastLogin( account.getLastLogin() );
        accountDto.setIsSuperAdmin( account.getIsSuperAdmin() );

        return accountDto;
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
        if ( requirement.getId() != null ) {
            requirementDto.setId( Long.parseLong( requirement.getId() ) );
        }
        requirementDto.setName( requirement.getName() );
        requirementDto.setDescription( requirement.getDescription() );
        requirementDto.setDevision( categoryToCategoryDto( requirement.getDevision() ) );
        requirementDto.setDetailClassification( categoryToCategoryDto( requirement.getDetailClassification() ) );
        requirementDto.setAcceptance( categoryToCategoryDto( requirement.getAcceptance() ) );
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

    protected TestExecutionDto testExecutionToTestExecutionDto(TestExecution testExecution) {
        if ( testExecution == null ) {
            return null;
        }

        TestExecutionDto testExecutionDto = new TestExecutionDto();

        testExecutionDto.setFlag( testExecution.getFlag() );
        if ( testExecution.getModifiedDate() != null ) {
            testExecutionDto.setModifiedDate( LocalDateTime.ofInstant( testExecution.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( testExecution.getCreatedDate() != null ) {
            testExecutionDto.setCreatedDate( LocalDateTime.ofInstant( testExecution.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testExecutionDto.setId( testExecution.getId() );
        testExecutionDto.setName( testExecution.getName() );
        testExecutionDto.setCategory( categoryToCategoryDto( testExecution.getCategory() ) );
        testExecutionDto.setStatus( categoryToCategoryDto( testExecution.getStatus() ) );
        testExecutionDto.setPlanStartDate( testExecution.getPlanStartDate() );
        testExecutionDto.setPlanEndDate( testExecution.getPlanEndDate() );
        testExecutionDto.setDetail( testExecution.getDetail() );
        testExecutionDto.setAssignedDeveloper( accountToAccountDto( testExecution.getAssignedDeveloper() ) );
        testExecutionDto.setProgram( programToProgramDto( testExecution.getProgram() ) );

        return testExecutionDto;
    }

    protected TestExecutionTurnDto testExecutionTurnToTestExecutionTurnDto(TestExecutionTurn testExecutionTurn) {
        if ( testExecutionTurn == null ) {
            return null;
        }

        TestExecutionTurnDto testExecutionTurnDto = new TestExecutionTurnDto();

        testExecutionTurnDto.setFlag( testExecutionTurn.getFlag() );
        if ( testExecutionTurn.getModifiedDate() != null ) {
            testExecutionTurnDto.setModifiedDate( LocalDateTime.ofInstant( testExecutionTurn.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( testExecutionTurn.getCreatedDate() != null ) {
            testExecutionTurnDto.setCreatedDate( LocalDateTime.ofInstant( testExecutionTurn.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testExecutionTurnDto.setId( testExecutionTurn.getId() );
        testExecutionTurnDto.setActualStartDate( testExecutionTurn.getActualStartDate() );
        testExecutionTurnDto.setActualEndDate( testExecutionTurn.getActualEndDate() );
        testExecutionTurnDto.setAssignedDeveloper( accountToAccountDto( testExecutionTurn.getAssignedDeveloper() ) );
        testExecutionTurnDto.setTestExecution( testExecutionToTestExecutionDto( testExecutionTurn.getTestExecution() ) );

        return testExecutionTurnDto;
    }
}
