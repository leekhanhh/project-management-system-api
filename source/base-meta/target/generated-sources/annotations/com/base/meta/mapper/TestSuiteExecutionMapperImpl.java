package com.base.meta.mapper;

import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.group.GroupDto;
import com.base.meta.dto.permission.PermissionDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.dto.testexecution.TestExecutionDto;
import com.base.meta.dto.testexecutionturn.TestExecutionTurnDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import com.base.meta.dto.testsuiteexecution.TestSuiteExecutionDto;
import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.Group;
import com.base.meta.model.Permission;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.Requirement;
import com.base.meta.model.TestExecution;
import com.base.meta.model.TestExecutionTurn;
import com.base.meta.model.TestSuite;
import com.base.meta.model.TestSuiteExecution;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-07T17:54:45+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestSuiteExecutionMapperImpl implements TestSuiteExecutionMapper {

    @Override
    public TestSuiteExecutionDto fromEntityToTestSuiteExecutionDto(TestSuiteExecution testSuiteExecution) {
        if ( testSuiteExecution == null ) {
            return null;
        }

        TestSuiteExecutionDto testSuiteExecutionDto = new TestSuiteExecutionDto();

        testSuiteExecutionDto.setOrderNumber( testSuiteExecution.getOrderNumber() );
        testSuiteExecutionDto.setTestSuite( testSuiteToTestSuiteDto( testSuiteExecution.getTestSuite() ) );
        testSuiteExecutionDto.setTestExecutionTurn( testExecutionTurnToTestExecutionTurnDto( testSuiteExecution.getTestExecutionTurn() ) );
        testSuiteExecutionDto.setId( testSuiteExecution.getId() );
        testSuiteExecutionDto.setDisplayId( testSuiteExecution.getDisplayId() );
        testSuiteExecutionDto.setStatus( categoryToCategoryDto( testSuiteExecution.getStatus() ) );

        return testSuiteExecutionDto;
    }

    @Override
    public List<TestSuiteExecutionDto> fromEntityToTestSuiteExecutionDtoList(List<TestSuiteExecution> testSuiteExecutionList) {
        if ( testSuiteExecutionList == null ) {
            return null;
        }

        List<TestSuiteExecutionDto> list = new ArrayList<TestSuiteExecutionDto>( testSuiteExecutionList.size() );
        for ( TestSuiteExecution testSuiteExecution : testSuiteExecutionList ) {
            list.add( fromEntityToTestSuiteExecutionDto( testSuiteExecution ) );
        }

        return list;
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
        permissionDto.setDisplayId( permission.getDisplayId() );

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
        groupDto.setDisplayId( group.getDisplayId() );

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
        accountDto.setDisplayId( account.getDisplayId() );

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
        categoryDto.setDisplayId( category.getDisplayId() );

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
        projectDto.setDisplayId( project.getDisplayId() );

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
        requirementDto.setDivision( categoryToCategoryDto( requirement.getDivision() ) );
        requirementDto.setDetailClassification( categoryToCategoryDto( requirement.getDetailClassification() ) );
        requirementDto.setAcceptance( categoryToCategoryDto( requirement.getAcceptance() ) );
        requirementDto.setProject( projectToProjectDto( requirement.getProject() ) );
        requirementDto.setDisplayId( requirement.getDisplayId() );

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
        programDto.setDisplayId( program.getDisplayId() );

        return programDto;
    }

    protected TestSuiteDto testSuiteToTestSuiteDto(TestSuite testSuite) {
        if ( testSuite == null ) {
            return null;
        }

        TestSuiteDto testSuiteDto = new TestSuiteDto();

        testSuiteDto.setFlag( testSuite.getFlag() );
        if ( testSuite.getModifiedDate() != null ) {
            testSuiteDto.setModifiedDate( LocalDateTime.ofInstant( testSuite.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( testSuite.getCreatedDate() != null ) {
            testSuiteDto.setCreatedDate( LocalDateTime.ofInstant( testSuite.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testSuiteDto.setId( testSuite.getId() );
        testSuiteDto.setName( testSuite.getName() );
        testSuiteDto.setDescription( testSuite.getDescription() );
        testSuiteDto.setAccount( accountToAccountDto( testSuite.getAccount() ) );
        testSuiteDto.setProgram( programToProgramDto( testSuite.getProgram() ) );
        testSuiteDto.setDisplayId( testSuite.getDisplayId() );

        return testSuiteDto;
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
        testExecutionDto.setDisplayId( testExecution.getDisplayId() );

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
        testExecutionTurnDto.setTurnNumber( testExecutionTurn.getTurnNumber() );
        testExecutionTurnDto.setActualStartDate( testExecutionTurn.getActualStartDate() );
        testExecutionTurnDto.setActualEndDate( testExecutionTurn.getActualEndDate() );
        testExecutionTurnDto.setAssignedDeveloper( accountToAccountDto( testExecutionTurn.getAssignedDeveloper() ) );
        testExecutionTurnDto.setTestExecution( testExecutionToTestExecutionDto( testExecutionTurn.getTestExecution() ) );
        testExecutionTurnDto.setDisplayId( testExecutionTurn.getDisplayId() );

        return testExecutionTurnDto;
    }
}
