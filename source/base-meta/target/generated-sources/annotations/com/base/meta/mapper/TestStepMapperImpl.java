package com.base.meta.mapper;

import com.base.meta.dto.teststep.TestStepDto;
import com.base.meta.form.teststep.CreateTestStepForm;
import com.base.meta.form.teststep.UpdateTestStepForm;
import com.base.meta.model.TestStep;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-08T16:34:09+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestStepMapperImpl implements TestStepMapper {

    @Autowired
    private TestCaseMapper testCaseMapper;

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
        testStepDto.setTestCase( testCaseMapper.fromEntityToTestCaseDtoAutoComplete( testStep.getTestCase() ) );

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

        testStepDto.setData( testStep.getData() );
        testStepDto.setExpectedResult( testStep.getExpectedResult() );
        testStepDto.setId( testStep.getId() );
        testStepDto.setStepNumber( testStep.getStepNumber() );

        return testStepDto;
    }
}
