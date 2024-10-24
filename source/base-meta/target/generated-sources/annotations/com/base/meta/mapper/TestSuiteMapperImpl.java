package com.base.meta.mapper;

import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.group.GroupDto;
import com.base.meta.dto.permission.PermissionDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.dto.testplan.TestPlanDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import com.base.meta.form.testsuite.CreateTestSuiteForm;
import com.base.meta.form.testsuite.UpdateTestSuiteForm;
import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.Group;
import com.base.meta.model.Permission;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.Requirement;
import com.base.meta.model.TestPlan;
import com.base.meta.model.TestSuite;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-24T19:17:11+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestSuiteMapperImpl implements TestSuiteMapper {

    @Override
    public TestSuite fromCreateTestSuiteFormToEntity(CreateTestSuiteForm createTestSuiteForm) {
        if ( createTestSuiteForm == null ) {
            return null;
        }

        TestSuite testSuite = new TestSuite();

        testSuite.setName( createTestSuiteForm.getName() );
        testSuite.setDescription( createTestSuiteForm.getDescription() );

        return testSuite;
    }

    @Override
    public TestSuiteDto fromEntityToTestSuiteDto(TestSuite testSuite) {
        if ( testSuite == null ) {
            return null;
        }

        TestSuiteDto testSuiteDto = new TestSuiteDto();

        testSuiteDto.setName( testSuite.getName() );
        testSuiteDto.setTestPlan( testPlanToTestPlanDto( testSuite.getTestPlan() ) );
        testSuiteDto.setDescription( testSuite.getDescription() );
        testSuiteDto.setId( testSuite.getId() );
        testSuiteDto.setAccount( accountToAccountDto( testSuite.getAccount() ) );

        return testSuiteDto;
    }

    @Override
    public List<TestSuiteDto> fromEntityToTestSuiteDtoList(List<TestSuite> testSuiteList) {
        if ( testSuiteList == null ) {
            return null;
        }

        List<TestSuiteDto> list = new ArrayList<TestSuiteDto>( testSuiteList.size() );
        for ( TestSuite testSuite : testSuiteList ) {
            list.add( fromEntityToTestSuiteDto( testSuite ) );
        }

        return list;
    }

    @Override
    public void updateTestSuiteFromEntity(UpdateTestSuiteForm updateTestSuiteForm, TestSuite testSuite) {
        if ( updateTestSuiteForm == null ) {
            return;
        }

        if ( updateTestSuiteForm.getName() != null ) {
            testSuite.setName( updateTestSuiteForm.getName() );
        }
        if ( updateTestSuiteForm.getDescription() != null ) {
            testSuite.setDescription( updateTestSuiteForm.getDescription() );
        }
    }

    @Override
    public TestSuiteDto fromEntityToTestSuiteDtoAutoComplete(TestSuite testSuite) {
        if ( testSuite == null ) {
            return null;
        }

        TestSuiteDto testSuiteDto = new TestSuiteDto();

        testSuiteDto.setName( testSuite.getName() );
        testSuiteDto.setId( testSuite.getId() );

        return testSuiteDto;
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

    protected TestPlanDto testPlanToTestPlanDto(TestPlan testPlan) {
        if ( testPlan == null ) {
            return null;
        }

        TestPlanDto testPlanDto = new TestPlanDto();

        testPlanDto.setFlag( testPlan.getFlag() );
        if ( testPlan.getModifiedDate() != null ) {
            testPlanDto.setModifiedDate( LocalDateTime.ofInstant( testPlan.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( testPlan.getCreatedDate() != null ) {
            testPlanDto.setCreatedDate( LocalDateTime.ofInstant( testPlan.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        testPlanDto.setId( testPlan.getId() );
        testPlanDto.setName( testPlan.getName() );
        testPlanDto.setDescription( testPlan.getDescription() );
        if ( testPlan.getStartDate() != null ) {
            testPlanDto.setStartDate( new SimpleDateFormat().format( testPlan.getStartDate() ) );
        }
        if ( testPlan.getEndDate() != null ) {
            testPlanDto.setEndDate( new SimpleDateFormat().format( testPlan.getEndDate() ) );
        }
        testPlanDto.setProgram( programToProgramDto( testPlan.getProgram() ) );

        return testPlanDto;
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
}
