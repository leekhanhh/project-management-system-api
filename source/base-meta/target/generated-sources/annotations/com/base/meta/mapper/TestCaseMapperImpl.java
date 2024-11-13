package com.base.meta.mapper;

import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.form.testcase.CreateTestCaseForm;
import com.base.meta.form.testcase.UpdateTestCaseForm;
import com.base.meta.model.TestCase;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-08T16:34:10+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestCaseMapperImpl implements TestCaseMapper {

    @Autowired
    private ProgramMapper programMapper;

    @Override
    public TestCase fromCreateTestCaseFormToEntity(CreateTestCaseForm createTestCaseForm) {
        if ( createTestCaseForm == null ) {
            return null;
        }

        TestCase testCase = new TestCase();

        testCase.setMenuPath( createTestCaseForm.getMenuPath() );
        testCase.setName( createTestCaseForm.getName() );
        testCase.setPrecondition( createTestCaseForm.getPrecondition() );

        return testCase;
    }

    @Override
    public TestCaseDto fromEntityToTestCaseDto(TestCase testCase) {
        if ( testCase == null ) {
            return null;
        }

        TestCaseDto testCaseDto = new TestCaseDto();

        testCaseDto.setMenuPath( testCase.getMenuPath() );
        testCaseDto.setName( testCase.getName() );
        testCaseDto.setId( testCase.getId() );
        testCaseDto.setPrecondition( testCase.getPrecondition() );
        testCaseDto.setProgram( programMapper.fromEntityToAutoCompleteProgramDto( testCase.getProgram() ) );

        return testCaseDto;
    }

    @Override
    public List<TestCaseDto> fromEntityToTestCaseDtoList(List<TestCase> testCases) {
        if ( testCases == null ) {
            return null;
        }

        List<TestCaseDto> list = new ArrayList<TestCaseDto>( testCases.size() );
        for ( TestCase testCase : testCases ) {
            list.add( fromEntityToTestCaseDto( testCase ) );
        }

        return list;
    }

    @Override
    public void fromUpdateTestCaseFormToEntity(UpdateTestCaseForm updateTestCaseForm, TestCase testCase) {
        if ( updateTestCaseForm == null ) {
            return;
        }

        if ( updateTestCaseForm.getMenuPath() != null ) {
            testCase.setMenuPath( updateTestCaseForm.getMenuPath() );
        }
        if ( updateTestCaseForm.getName() != null ) {
            testCase.setName( updateTestCaseForm.getName() );
        }
        if ( updateTestCaseForm.getPrecondition() != null ) {
            testCase.setPrecondition( updateTestCaseForm.getPrecondition() );
        }
    }

    @Override
    public TestCaseDto fromEntityToTestCaseDtoAutoComplete(TestCase testCase) {
        if ( testCase == null ) {
            return null;
        }

        TestCaseDto testCaseDto = new TestCaseDto();

        testCaseDto.setMenuPath( testCase.getMenuPath() );
        testCaseDto.setName( testCase.getName() );
        testCaseDto.setId( testCase.getId() );
        testCaseDto.setPrecondition( testCase.getPrecondition() );

        return testCaseDto;
    }
}
