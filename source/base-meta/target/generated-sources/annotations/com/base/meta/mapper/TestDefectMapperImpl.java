package com.base.meta.mapper;

import com.base.meta.dto.testdefect.TestDefectDto;
import com.base.meta.form.testdefect.CreateTestDefectForm;
import com.base.meta.form.testdefect.UpdateTestDefectForm;
import com.base.meta.model.TestDefect;
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
public class TestDefectMapperImpl implements TestDefectMapper {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TestStepExecutionMapper testStepExecutionMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public TestDefect fromCreateTestDefectFormToEntity(CreateTestDefectForm createTestDefectForm) {
        if ( createTestDefectForm == null ) {
            return null;
        }

        TestDefect testDefect = new TestDefect();

        testDefect.setSeverity( createTestDefectForm.getSeverity() );
        testDefect.setDescription( createTestDefectForm.getDescription() );
        testDefect.setPriority( createTestDefectForm.getPriority() );
        testDefect.setSendEmailNotification( createTestDefectForm.getSendEmailNotification() );
        testDefect.setName( createTestDefectForm.getName() );

        return testDefect;
    }

    @Override
    public TestDefectDto fromEntityToDto(TestDefect testDefect) {
        if ( testDefect == null ) {
            return null;
        }

        TestDefectDto testDefectDto = new TestDefectDto();

        testDefectDto.setSeverity( testDefect.getSeverity() );
        testDefectDto.setDescription( testDefect.getDescription() );
        testDefectDto.setPriority( testDefect.getPriority() );
        testDefectDto.setTestStepExecution( testStepExecutionMapper.fromEntityToDto( testDefect.getTestStepExecution() ) );
        testDefectDto.setName( testDefect.getName() );
        testDefectDto.setAssignedDeveloper( accountMapper.fromAccountToDto( testDefect.getAssignedDeveloper() ) );
        testDefectDto.setId( testDefect.getId() );
        testDefectDto.setDisplayId( testDefect.getDisplayId() );
        testDefectDto.setStatus( categoryMapper.fromEntityToAutoCompleteNameToDto( testDefect.getStatus() ) );

        return testDefectDto;
    }

    @Override
    public List<TestDefectDto> fromEntityListToDtoList(List<TestDefect> testDefectList) {
        if ( testDefectList == null ) {
            return null;
        }

        List<TestDefectDto> list = new ArrayList<TestDefectDto>( testDefectList.size() );
        for ( TestDefect testDefect : testDefectList ) {
            list.add( fromEntityToDto( testDefect ) );
        }

        return list;
    }

    @Override
    public TestDefectDto fromEntityToDtoAutoComplete(TestDefect testDefect) {
        if ( testDefect == null ) {
            return null;
        }

        TestDefectDto testDefectDto = new TestDefectDto();

        testDefectDto.setName( testDefect.getName() );
        testDefectDto.setId( testDefect.getId() );

        return testDefectDto;
    }

    @Override
    public void mappingUpdateTestDefectFormToEntity(UpdateTestDefectForm updateTestDefectForm, TestDefect testDefect) {
        if ( updateTestDefectForm == null ) {
            return;
        }

        if ( updateTestDefectForm.getSeverity() != null ) {
            testDefect.setSeverity( updateTestDefectForm.getSeverity() );
        }
        if ( updateTestDefectForm.getDescription() != null ) {
            testDefect.setDescription( updateTestDefectForm.getDescription() );
        }
        if ( updateTestDefectForm.getPriority() != null ) {
            testDefect.setPriority( updateTestDefectForm.getPriority() );
        }
        if ( updateTestDefectForm.getSendEmailNotification() != null ) {
            testDefect.setSendEmailNotification( updateTestDefectForm.getSendEmailNotification() );
        }
        if ( updateTestDefectForm.getName() != null ) {
            testDefect.setName( updateTestDefectForm.getName() );
        }
    }
}
