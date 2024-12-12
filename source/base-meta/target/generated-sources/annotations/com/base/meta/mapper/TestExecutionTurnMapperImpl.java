package com.base.meta.mapper;

import com.base.meta.dto.testexecutionturn.TestExecutionTurnDto;
import com.base.meta.form.testexecutionturn.CreateTestExecutionTurnForm;
import com.base.meta.form.testexecutionturn.UpdateTestExecutionTurnForm;
import com.base.meta.model.TestExecutionTurn;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-09T22:05:24+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestExecutionTurnMapperImpl implements TestExecutionTurnMapper {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TestExecutionMapper testExecutionMapper;

    @Override
    public TestExecutionTurn fromCreateTestExecutionTurnFormToEntity(CreateTestExecutionTurnForm createTestExecutionTurnForm) {
        if ( createTestExecutionTurnForm == null ) {
            return null;
        }

        TestExecutionTurn testExecutionTurn = new TestExecutionTurn();

        testExecutionTurn.setActualEndDate( createTestExecutionTurnForm.getActualEndDate() );
        testExecutionTurn.setTurnNumber( createTestExecutionTurnForm.getTurnNumber() );
        testExecutionTurn.setActualStartDate( createTestExecutionTurnForm.getActualStartDate() );
        testExecutionTurn.setPlanStartDate( createTestExecutionTurnForm.getPlanStartDate() );
        testExecutionTurn.setPlanEndDate( createTestExecutionTurnForm.getPlanEndDate() );

        return testExecutionTurn;
    }

    @Override
    public TestExecutionTurnDto fromEntityToDto(TestExecutionTurn testExecutionTurn) {
        if ( testExecutionTurn == null ) {
            return null;
        }

        TestExecutionTurnDto testExecutionTurnDto = new TestExecutionTurnDto();

        testExecutionTurnDto.setActualEndDate( testExecutionTurn.getActualEndDate() );
        testExecutionTurnDto.setEndDate( testExecutionTurn.getPlanEndDate() );
        testExecutionTurnDto.setTestExecution( testExecutionMapper.fromEntityToTestExecutionDtoAutoComplete( testExecutionTurn.getTestExecution() ) );
        testExecutionTurnDto.setTurnNumber( testExecutionTurn.getTurnNumber() );
        testExecutionTurnDto.setActualStartDate( testExecutionTurn.getActualStartDate() );
        testExecutionTurnDto.setAssignedDeveloper( accountMapper.fromAccountToDto( testExecutionTurn.getAssignedDeveloper() ) );
        testExecutionTurnDto.setId( testExecutionTurn.getId() );
        testExecutionTurnDto.setDisplayId( testExecutionTurn.getDisplayId() );
        testExecutionTurnDto.setStartDate( testExecutionTurn.getPlanStartDate() );

        return testExecutionTurnDto;
    }

    @Override
    public List<TestExecutionTurnDto> fromEntitiesToDtos(List<TestExecutionTurn> testExecutionTurns) {
        if ( testExecutionTurns == null ) {
            return null;
        }

        List<TestExecutionTurnDto> list = new ArrayList<TestExecutionTurnDto>( testExecutionTurns.size() );
        for ( TestExecutionTurn testExecutionTurn : testExecutionTurns ) {
            list.add( fromEntityToDto( testExecutionTurn ) );
        }

        return list;
    }

    @Override
    public void updateTestExecutionTurnFromToEntity(UpdateTestExecutionTurnForm updateTestExecutionTurnForm, TestExecutionTurn testExecutionTurn) {
        if ( updateTestExecutionTurnForm == null ) {
            return;
        }

        if ( updateTestExecutionTurnForm.getActualEndDate() != null ) {
            testExecutionTurn.setActualEndDate( updateTestExecutionTurnForm.getActualEndDate() );
        }
        if ( updateTestExecutionTurnForm.getTurnNumber() != null ) {
            testExecutionTurn.setTurnNumber( updateTestExecutionTurnForm.getTurnNumber() );
        }
        if ( updateTestExecutionTurnForm.getActualStartDate() != null ) {
            testExecutionTurn.setActualStartDate( updateTestExecutionTurnForm.getActualStartDate() );
        }
        if ( updateTestExecutionTurnForm.getPlanStartDate() != null ) {
            testExecutionTurn.setPlanStartDate( updateTestExecutionTurnForm.getPlanStartDate() );
        }
        if ( updateTestExecutionTurnForm.getPlanEndDate() != null ) {
            testExecutionTurn.setPlanEndDate( updateTestExecutionTurnForm.getPlanEndDate() );
        }
    }

    @Override
    public TestExecutionTurnDto fromEntityToAutoCompleteTestExecutionTurnDto(TestExecutionTurn testExecutionTurn) {
        if ( testExecutionTurn == null ) {
            return null;
        }

        TestExecutionTurnDto testExecutionTurnDto = new TestExecutionTurnDto();

        testExecutionTurnDto.setEndDate( testExecutionTurn.getPlanEndDate() );
        testExecutionTurnDto.setTurnNumber( testExecutionTurn.getTurnNumber() );
        testExecutionTurnDto.setId( testExecutionTurn.getId() );
        testExecutionTurnDto.setStartDate( testExecutionTurn.getPlanStartDate() );

        return testExecutionTurnDto;
    }
}
