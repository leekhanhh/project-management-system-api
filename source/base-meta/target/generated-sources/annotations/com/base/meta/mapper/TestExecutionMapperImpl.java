package com.base.meta.mapper;

import com.base.meta.dto.testexecution.TestExecutionDto;
import com.base.meta.form.testexecution.CreateTestExecutionForm;
import com.base.meta.form.testexecution.UpdateTestExecutionForm;
import com.base.meta.model.TestExecution;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-25T20:49:55+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestExecutionMapperImpl implements TestExecutionMapper {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ProgramMapper programMapper;
    @Autowired
    private CategoryMapper categoryMapper;

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

        testExecutionDto.setProgram( programMapper.fromEntityToAutoCompleteProgramDto( testExecution.getProgram() ) );
        testExecutionDto.setName( testExecution.getName() );
        testExecutionDto.setAssignedDeveloper( accountMapper.fromAccountToDto( testExecution.getAssignedDeveloper() ) );
        testExecutionDto.setId( testExecution.getId() );
        testExecutionDto.setDetail( testExecution.getDetail() );
        testExecutionDto.setCategory( categoryMapper.fromEntityToAutoCompleteNameToDto( testExecution.getCategory() ) );
        testExecutionDto.setPlanStartDate( testExecution.getPlanStartDate() );
        testExecutionDto.setDisplayId( testExecution.getDisplayId() );
        testExecutionDto.setPlanEndDate( testExecution.getPlanEndDate() );
        testExecutionDto.setStatus( categoryMapper.fromEntityToAutoCompleteNameToDto( testExecution.getStatus() ) );

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
        testExecutionDto.setCategory( categoryMapper.fromEntityToAutoCompleteNameToDto( testExecution.getCategory() ) );

        return testExecutionDto;
    }
}
