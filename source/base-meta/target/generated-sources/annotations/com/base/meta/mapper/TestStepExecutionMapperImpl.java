package com.base.meta.mapper;

import com.base.meta.dto.teststepexecution.TestStepExecutionDto;
import com.base.meta.model.TestStepExecution;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-07T17:54:45+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestStepExecutionMapperImpl implements TestStepExecutionMapper {

    @Autowired
    private TestStepMapper testStepMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TestCaseExecutionMapper testCaseExecutionMapper;

    @Override
    public TestStepExecutionDto fromEntityToDto(TestStepExecution testStepExecution) {
        if ( testStepExecution == null ) {
            return null;
        }

        TestStepExecutionDto testStepExecutionDto = new TestStepExecutionDto();

        testStepExecutionDto.setIsDefected( testStepExecution.getIsDefected() );
        testStepExecutionDto.setTestCaseExecution( testCaseExecutionMapper.fromEntityToTestCaseExecutionDto( testStepExecution.getTestCaseExecution() ) );
        testStepExecutionDto.setId( testStepExecution.getId() );
        testStepExecutionDto.setTestStep( testStepMapper.fromEntityToAutoComplete( testStepExecution.getTestStep() ) );
        testStepExecutionDto.setDisplayId( testStepExecution.getDisplayId() );
        testStepExecutionDto.setStatus( categoryMapper.fromEntityToAutoCompleteNameToDto( testStepExecution.getStatus() ) );

        return testStepExecutionDto;
    }

    @Override
    public List<TestStepExecutionDto> fromEntityListToDtoList(List<TestStepExecution> testStepExecutionList) {
        if ( testStepExecutionList == null ) {
            return null;
        }

        List<TestStepExecutionDto> list = new ArrayList<TestStepExecutionDto>( testStepExecutionList.size() );
        for ( TestStepExecution testStepExecution : testStepExecutionList ) {
            list.add( fromEntityToDto( testStepExecution ) );
        }

        return list;
    }
}
