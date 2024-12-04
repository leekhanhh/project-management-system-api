package com.base.meta.mapper;

import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.group.GroupDto;
import com.base.meta.dto.permission.PermissionDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import com.base.meta.dto.testsuitetestcaseralation.TestSuiteTestCaseRelationDto;
import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.Group;
import com.base.meta.model.Permission;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.Requirement;
import com.base.meta.model.TestCase;
import com.base.meta.model.TestSuite;
import com.base.meta.model.TestSuiteTestCaseRelation;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-02T17:51:40+0700",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class TestSuiteTestCaseRelationMapperImpl implements TestSuiteTestCaseRelationMapper {

    @Override
    public TestSuiteTestCaseRelationDto fromEntityToDto(TestSuiteTestCaseRelation testSuiteTestCaseRelation) {
        if ( testSuiteTestCaseRelation == null ) {
            return null;
        }

        TestSuiteTestCaseRelationDto testSuiteTestCaseRelationDto = new TestSuiteTestCaseRelationDto();

        testSuiteTestCaseRelationDto.setTestSuite( testSuiteToTestSuiteDto( testSuiteTestCaseRelation.getTestSuite() ) );
        testSuiteTestCaseRelationDto.setTestCase( testCaseToTestCaseDto( testSuiteTestCaseRelation.getTestCase() ) );

        return testSuiteTestCaseRelationDto;
    }

    @Override
    public List<TestSuiteTestCaseRelationDto> fromEntitiesToDtos(List<TestSuiteTestCaseRelation> testSuiteTestCaseRelations) {
        if ( testSuiteTestCaseRelations == null ) {
            return null;
        }

        List<TestSuiteTestCaseRelationDto> list = new ArrayList<TestSuiteTestCaseRelationDto>( testSuiteTestCaseRelations.size() );
        for ( TestSuiteTestCaseRelation testSuiteTestCaseRelation : testSuiteTestCaseRelations ) {
            list.add( fromEntityToDto( testSuiteTestCaseRelation ) );
        }

        return list;
    }

    protected PermissionDto permissionToPermissionDto(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionDto permissionDto = new PermissionDto();

        if ( permission.getCreatedDate() != null ) {
            permissionDto.setCreatedDate( LocalDateTime.ofInstant( permission.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        permissionDto.setFlag( permission.getFlag() );
        if ( permission.getModifiedDate() != null ) {
            permissionDto.setModifiedDate( LocalDateTime.ofInstant( permission.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        permissionDto.setAction( permission.getAction() );
        permissionDto.setDescription( permission.getDescription() );
        permissionDto.setDisplayId( permission.getDisplayId() );
        permissionDto.setId( permission.getId() );
        permissionDto.setKind( permission.getKind() );
        permissionDto.setName( permission.getName() );
        permissionDto.setNameGroup( permission.getNameGroup() );
        permissionDto.setPermissionCode( permission.getPermissionCode() );
        permissionDto.setShowMenu( permission.getShowMenu() );

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

        if ( group.getCreatedDate() != null ) {
            groupDto.setCreatedDate( LocalDateTime.ofInstant( group.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        groupDto.setFlag( group.getFlag() );
        if ( group.getModifiedDate() != null ) {
            groupDto.setModifiedDate( LocalDateTime.ofInstant( group.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        groupDto.setDescription( group.getDescription() );
        groupDto.setDisplayId( group.getDisplayId() );
        groupDto.setId( group.getId() );
        groupDto.setIsSystemRole( group.getIsSystemRole() );
        groupDto.setKind( group.getKind() );
        groupDto.setName( group.getName() );
        groupDto.setPermissions( permissionListToPermissionDtoList( group.getPermissions() ) );

        return groupDto;
    }

    protected AccountDto accountToAccountDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        if ( account.getCreatedDate() != null ) {
            accountDto.setCreatedDate( LocalDateTime.ofInstant( account.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        accountDto.setFlag( account.getFlag() );
        if ( account.getModifiedDate() != null ) {
            accountDto.setModifiedDate( LocalDateTime.ofInstant( account.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        accountDto.setDisplayId( account.getDisplayId() );
        accountDto.setEmail( account.getEmail() );
        accountDto.setFullName( account.getFullName() );
        accountDto.setGroup( groupToGroupDto( account.getGroup() ) );
        accountDto.setId( account.getId() );
        accountDto.setIsSuperAdmin( account.getIsSuperAdmin() );
        accountDto.setKind( account.getKind() );
        accountDto.setLastLogin( account.getLastLogin() );
        accountDto.setPhone( account.getPhone() );
        accountDto.setUsername( account.getUsername() );

        return accountDto;
    }

    protected CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        if ( category.getCreatedDate() != null ) {
            categoryDto.setCreatedDate( LocalDateTime.ofInstant( category.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        categoryDto.setFlag( category.getFlag() );
        if ( category.getModifiedDate() != null ) {
            categoryDto.setModifiedDate( LocalDateTime.ofInstant( category.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        categoryDto.setDisplayId( category.getDisplayId() );
        categoryDto.setId( category.getId() );

        return categoryDto;
    }

    protected ProjectDto projectToProjectDto(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDto projectDto = new ProjectDto();

        if ( project.getCreatedDate() != null ) {
            projectDto.setCreatedDate( LocalDateTime.ofInstant( project.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        projectDto.setFlag( project.getFlag() );
        if ( project.getModifiedDate() != null ) {
            projectDto.setModifiedDate( LocalDateTime.ofInstant( project.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        projectDto.setDescription( project.getDescription() );
        projectDto.setDisplayId( project.getDisplayId() );
        projectDto.setEndDate( project.getEndDate() );
        projectDto.setId( project.getId() );
        projectDto.setName( project.getName() );
        projectDto.setStartDate( project.getStartDate() );
        projectDto.setStatus( categoryToCategoryDto( project.getStatus() ) );

        return projectDto;
    }

    protected RequirementDto requirementToRequirementDto(Requirement requirement) {
        if ( requirement == null ) {
            return null;
        }

        RequirementDto requirementDto = new RequirementDto();

        if ( requirement.getCreatedDate() != null ) {
            requirementDto.setCreatedDate( LocalDateTime.ofInstant( requirement.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        requirementDto.setFlag( requirement.getFlag() );
        if ( requirement.getModifiedDate() != null ) {
            requirementDto.setModifiedDate( LocalDateTime.ofInstant( requirement.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        requirementDto.setAcceptance( requirement.getAcceptance() );
        requirementDto.setDescription( requirement.getDescription() );
        requirementDto.setDetailClassification( categoryToCategoryDto( requirement.getDetailClassification() ) );
        requirementDto.setDevision( categoryToCategoryDto( requirement.getDevision() ) );
        requirementDto.setDisplayId( requirement.getDisplayId() );
        requirementDto.setId( requirement.getId() );
        requirementDto.setName( categoryToCategoryDto( requirement.getName() ) );
        requirementDto.setProject( projectToProjectDto( requirement.getProject() ) );

        return requirementDto;
    }

    protected ProgramDto programToProgramDto(Program program) {
        if ( program == null ) {
            return null;
        }

        ProgramDto programDto = new ProgramDto();

        if ( program.getCreatedDate() != null ) {
            programDto.setCreatedDate( LocalDateTime.ofInstant( program.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        programDto.setFlag( program.getFlag() );
        if ( program.getModifiedDate() != null ) {
            programDto.setModifiedDate( LocalDateTime.ofInstant( program.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        programDto.setDescription( program.getDescription() );
        programDto.setDisplayId( program.getDisplayId() );
        if ( program.getEndDate() != null ) {
            programDto.setEndDate( new SimpleDateFormat().format( program.getEndDate() ) );
        }
        programDto.setId( program.getId() );
        programDto.setName( program.getName() );
        programDto.setProgramCategory( program.getProgramCategory() );
        programDto.setProgramStatus( categoryToCategoryDto( program.getProgramStatus() ) );
        programDto.setProgramType( categoryToCategoryDto( program.getProgramType() ) );
        programDto.setProject( projectToProjectDto( program.getProject() ) );
        programDto.setRequirement( requirementToRequirementDto( program.getRequirement() ) );
        if ( program.getStartDate() != null ) {
            programDto.setStartDate( new SimpleDateFormat().format( program.getStartDate() ) );
        }

        return programDto;
    }

    protected TestSuiteDto testSuiteToTestSuiteDto(TestSuite testSuite) {
        if ( testSuite == null ) {
            return null;
        }

        TestSuiteDto testSuiteDto = new TestSuiteDto();

        if ( testSuite.getCreatedDate() != null ) {
            testSuiteDto.setCreatedDate( LocalDateTime.ofInstant( testSuite.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testSuiteDto.setFlag( testSuite.getFlag() );
        if ( testSuite.getModifiedDate() != null ) {
            testSuiteDto.setModifiedDate( LocalDateTime.ofInstant( testSuite.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testSuiteDto.setAccount( accountToAccountDto( testSuite.getAccount() ) );
        testSuiteDto.setDescription( testSuite.getDescription() );
        testSuiteDto.setDisplayId( testSuite.getDisplayId() );
        testSuiteDto.setId( testSuite.getId() );
        testSuiteDto.setName( testSuite.getName() );
        testSuiteDto.setProgram( programToProgramDto( testSuite.getProgram() ) );

        return testSuiteDto;
    }

    protected TestCaseDto testCaseToTestCaseDto(TestCase testCase) {
        if ( testCase == null ) {
            return null;
        }

        TestCaseDto testCaseDto = new TestCaseDto();

        if ( testCase.getCreatedDate() != null ) {
            testCaseDto.setCreatedDate( LocalDateTime.ofInstant( testCase.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testCaseDto.setFlag( testCase.getFlag() );
        if ( testCase.getModifiedDate() != null ) {
            testCaseDto.setModifiedDate( LocalDateTime.ofInstant( testCase.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testCaseDto.setDisplayId( testCase.getDisplayId() );
        testCaseDto.setId( testCase.getId() );
        testCaseDto.setMenuPath( testCase.getMenuPath() );
        testCaseDto.setName( testCase.getName() );
        testCaseDto.setPrecondition( testCase.getPrecondition() );
        testCaseDto.setProgram( programToProgramDto( testCase.getProgram() ) );

        return testCaseDto;
    }
}
