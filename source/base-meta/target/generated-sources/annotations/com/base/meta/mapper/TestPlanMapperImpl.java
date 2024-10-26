package com.base.meta.mapper;

import com.base.meta.dto.testplan.TestPlanDto;
import com.base.meta.form.testplan.CreateTestPlanForm;
import com.base.meta.form.testplan.UpdateTestPlanForm;
import com.base.meta.model.TestPlan;
import java.text.SimpleDateFormat;
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
public class TestPlanMapperImpl implements TestPlanMapper {

    @Autowired
    private ProgramMapper programMapper;

    @Override
    public TestPlan fromCreateTestPlanFormToEntity(CreateTestPlanForm createTestPlanForm) {
        if ( createTestPlanForm == null ) {
            return null;
        }

        TestPlan testPlan = new TestPlan();

        testPlan.setEndDate( createTestPlanForm.getEndDate() );
        testPlan.setName( createTestPlanForm.getName() );
        testPlan.setDescription( createTestPlanForm.getDescription() );
        testPlan.setStartDate( createTestPlanForm.getStartDate() );

        return testPlan;
    }

    @Override
    public TestPlanDto fromEntityToTestPlanDto(TestPlan testPlan) {
        if ( testPlan == null ) {
            return null;
        }

        TestPlanDto testPlanDto = new TestPlanDto();

        if ( testPlan.getEndDate() != null ) {
            testPlanDto.setEndDate( new SimpleDateFormat().format( testPlan.getEndDate() ) );
        }
        testPlanDto.setName( testPlan.getName() );
        testPlanDto.setDescription( testPlan.getDescription() );
        testPlanDto.setId( testPlan.getId() );
        testPlanDto.setProgram( programMapper.fromEntityToAutoCompleteProgramDto( testPlan.getProgram() ) );
        if ( testPlan.getStartDate() != null ) {
            testPlanDto.setStartDate( new SimpleDateFormat().format( testPlan.getStartDate() ) );
        }

        return testPlanDto;
    }

    @Override
    public List<TestPlanDto> fromEntityToTestPlanDtoList(List<TestPlan> testPlans) {
        if ( testPlans == null ) {
            return null;
        }

        List<TestPlanDto> list = new ArrayList<TestPlanDto>( testPlans.size() );
        for ( TestPlan testPlan : testPlans ) {
            list.add( fromEntityToTestPlanDto( testPlan ) );
        }

        return list;
    }

    @Override
    public void updateTestPlanFromEntity(UpdateTestPlanForm updateTestPlanForm, TestPlan testPlan) {
        if ( updateTestPlanForm == null ) {
            return;
        }

        if ( updateTestPlanForm.getEndDate() != null ) {
            testPlan.setEndDate( updateTestPlanForm.getEndDate() );
        }
        if ( updateTestPlanForm.getName() != null ) {
            testPlan.setName( updateTestPlanForm.getName() );
        }
        if ( updateTestPlanForm.getDescription() != null ) {
            testPlan.setDescription( updateTestPlanForm.getDescription() );
        }
        if ( updateTestPlanForm.getStartDate() != null ) {
            testPlan.setStartDate( updateTestPlanForm.getStartDate() );
        }
    }

    @Override
    public TestPlanDto fromEntityToTestPlanDtoAutoComplete(TestPlan testPlan) {
        if ( testPlan == null ) {
            return null;
        }

        TestPlanDto testPlanDto = new TestPlanDto();

        testPlanDto.setName( testPlan.getName() );
        testPlanDto.setId( testPlan.getId() );

        return testPlanDto;
    }
}
