package com.base.meta.mapper;

import com.base.meta.dto.testcaseexecution.TestCaseExecutionDto;
import com.base.meta.form.testcaseexecution.UpdateTestCaseExecutionForm;
import com.base.meta.model.TestCaseExecution;
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
public class TestCaseExecutionMapperImpl implements TestCaseExecutionMapper {

    @Autowired
    private TestCaseMapper testCaseMapper;
    @Autowired
    private TestSuiteMapper testSuiteMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TestExecutionTurnMapper testExecutionTurnMapper;

    @Override
    public TestCaseExecutionDto fromEntityToTestCaseExecutionDto(TestCaseExecution testCaseExecution) {
        if ( testCaseExecution == null ) {
            return null;
        }

        TestCaseExecutionDto testCaseExecutionDto = new TestCaseExecutionDto();

        testCaseExecutionDto.setTestExecutionTypeCode( categoryMapper.fromEntityToAutoCompleteNameToDto( testCaseExecution.getTestExecutionTypeCode() ) );
        testCaseExecutionDto.setTestSuite( testSuiteMapper.fromEntityToTestSuiteDtoAutoComplete( testCaseExecution.getTestSuite() ) );
        testCaseExecutionDto.setTestExecutionTurn( testExecutionTurnMapper.fromEntityToAutoCompleteTestExecutionTurnDto( testCaseExecution.getTestExecutionTurn() ) );
        testCaseExecutionDto.setId( testCaseExecution.getId() );
        testCaseExecutionDto.setDisplayId( testCaseExecution.getDisplayId() );
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
}
