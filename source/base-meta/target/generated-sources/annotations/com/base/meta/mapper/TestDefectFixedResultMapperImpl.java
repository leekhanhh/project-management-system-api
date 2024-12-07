package com.base.meta.mapper;

import com.base.meta.dto.testdefectfixedresult.TestDefectFixedResultDto;
import com.base.meta.form.testdefectfixedresult.CreateTestDefectFixedResultForm;
import com.base.meta.form.testdefectfixedresult.UpdateTestDefectFixedResultForm;
import com.base.meta.model.TestDefectFixedResult;
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
public class TestDefectFixedResultMapperImpl implements TestDefectFixedResultMapper {

    @Autowired
    private TestDefectMapper testDefectMapper;

    @Override
    public TestDefectFixedResult fromCreateTestDefectFixedResultFormToEntity(CreateTestDefectFixedResultForm createTestDefectFixedResultForm) {
        if ( createTestDefectFixedResultForm == null ) {
            return null;
        }

        TestDefectFixedResult testDefectFixedResult = new TestDefectFixedResult();

        testDefectFixedResult.setActionStartDate( createTestDefectFixedResultForm.getActionStartDate() );
        testDefectFixedResult.setDescription( createTestDefectFixedResultForm.getDescription() );
        testDefectFixedResult.setRemark( createTestDefectFixedResultForm.getRemark() );

        return testDefectFixedResult;
    }

    @Override
    public void mappingUpdateTestDefectFixedResultFormToEntity(UpdateTestDefectFixedResultForm updateTestDefectFixedResultForm, TestDefectFixedResult testDefectFixedResult) {
        if ( updateTestDefectFixedResultForm == null ) {
            return;
        }

        if ( updateTestDefectFixedResultForm.getActionStartDate() != null ) {
            testDefectFixedResult.setActionStartDate( updateTestDefectFixedResultForm.getActionStartDate() );
        }
        if ( updateTestDefectFixedResultForm.getDescription() != null ) {
            testDefectFixedResult.setDescription( updateTestDefectFixedResultForm.getDescription() );
        }
        if ( updateTestDefectFixedResultForm.getRemark() != null ) {
            testDefectFixedResult.setRemark( updateTestDefectFixedResultForm.getRemark() );
        }
    }

    @Override
    public TestDefectFixedResultDto fromEntityToDto(TestDefectFixedResult testDefectFixedResult) {
        if ( testDefectFixedResult == null ) {
            return null;
        }

        TestDefectFixedResultDto testDefectFixedResultDto = new TestDefectFixedResultDto();

        testDefectFixedResultDto.setFixedDate( testDefectFixedResult.getActionStartDate() );
        testDefectFixedResultDto.setDescription( testDefectFixedResult.getDescription() );
        testDefectFixedResultDto.setTestDefect( testDefectMapper.fromEntityToDtoAutoComplete( testDefectFixedResult.getTestDefect() ) );
        testDefectFixedResultDto.setRemark( testDefectFixedResult.getRemark() );
        testDefectFixedResultDto.setId( testDefectFixedResult.getId() );
        testDefectFixedResultDto.setDisplayId( testDefectFixedResult.getDisplayId() );

        return testDefectFixedResultDto;
    }

    @Override
    public List<TestDefectFixedResultDto> fromEntityListToDtoList(List<TestDefectFixedResult> testDefectFixedResultList) {
        if ( testDefectFixedResultList == null ) {
            return null;
        }

        List<TestDefectFixedResultDto> list = new ArrayList<TestDefectFixedResultDto>( testDefectFixedResultList.size() );
        for ( TestDefectFixedResult testDefectFixedResult : testDefectFixedResultList ) {
            list.add( fromEntityToDto( testDefectFixedResult ) );
        }

        return list;
    }
}
