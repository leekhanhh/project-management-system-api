package com.base.meta.mapper;

import com.base.meta.dto.testcaseupload.TestCaseUploadDto;
import com.base.meta.model.TestCaseUpload;
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
public class TestCaseUploadMapperImpl implements TestCaseUploadMapper {

    @Autowired
    private ProgramMapper programMapper;

    @Override
    public TestCaseUploadDto fromEntityToTestCaseUploadDto(TestCaseUpload testCaseUpload) {
        if ( testCaseUpload == null ) {
            return null;
        }

        TestCaseUploadDto testCaseUploadDto = new TestCaseUploadDto();

        testCaseUploadDto.setTestStepAction( testCaseUpload.getTestStepAction() );
        testCaseUploadDto.setTestCasePrecondition( testCaseUpload.getTestCasePrecondition() );
        testCaseUploadDto.setTestStepData( testCaseUpload.getTestStepData() );
        testCaseUploadDto.setId( testCaseUpload.getId() );
        testCaseUploadDto.setTestCaseName( testCaseUpload.getTestCaseName() );
        testCaseUploadDto.setProgram( programMapper.fromEntityToAutoCompleteProgramDto( testCaseUpload.getProgram() ) );
        testCaseUploadDto.setTestStepExpectedResult( testCaseUpload.getTestStepExpectedResult() );
        testCaseUploadDto.setTestCaseMenuPath( testCaseUpload.getTestCaseMenuPath() );

        return testCaseUploadDto;
    }

    @Override
    public List<TestCaseUploadDto> fromEntityToTestCaseUploadDtoList(List<TestCaseUpload> testCaseUploads) {
        if ( testCaseUploads == null ) {
            return null;
        }

        List<TestCaseUploadDto> list = new ArrayList<TestCaseUploadDto>( testCaseUploads.size() );
        for ( TestCaseUpload testCaseUpload : testCaseUploads ) {
            list.add( fromEntityToTestCaseUploadDto( testCaseUpload ) );
        }

        return list;
    }
}
