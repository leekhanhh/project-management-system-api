package com.base.meta.mapper;

import com.base.meta.dto.testsuite.TestSuiteDto;
import com.base.meta.form.testsuite.CreateTestSuiteForm;
import com.base.meta.form.testsuite.UpdateTestSuiteForm;
import com.base.meta.model.TestSuite;
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
public class TestSuiteMapperImpl implements TestSuiteMapper {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ProgramMapper programMapper;

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
        testSuiteDto.setDescription( testSuite.getDescription() );
        testSuiteDto.setId( testSuite.getId() );
        testSuiteDto.setProgram( programMapper.fromEntityToAutoCompleteProgramDto( testSuite.getProgram() ) );
        testSuiteDto.setDisplayId( testSuite.getDisplayId() );
        testSuiteDto.setAccount( accountMapper.fromAccountToDto( testSuite.getAccount() ) );

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
}
