package com.base.meta.mapper;

import com.base.meta.dto.testcaseupload.TestCaseUploadDto;
import com.base.meta.form.testcaseupload.CreateTestCaseUploadForm;
import com.base.meta.model.Program;
import com.base.meta.model.TestCaseUpload;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-24T19:17:10+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestCaseUploadMapperImpl implements TestCaseUploadMapper {

    @Autowired
    private ProgramMapper programMapper;

    @Override
    public TestCaseUpload fromCreateTestCaseUploadFormToEntity(CreateTestCaseUploadForm createTestCaseUploadForm) {
        if ( createTestCaseUploadForm == null ) {
            return null;
        }

        TestCaseUpload testCaseUpload = new TestCaseUpload();

        testCaseUpload.setProgram( createTestCaseUploadFormToProgram( createTestCaseUploadForm ) );
        testCaseUpload.setTestStepsData( createTestCaseUploadForm.getTestStepsData() );
        testCaseUpload.setTestStepsExpectedResult( createTestCaseUploadForm.getTestStepsExpectedResult() );
        testCaseUpload.setTestCasePrecondition( createTestCaseUploadForm.getTestCasePrecondition() );
        testCaseUpload.setTestStepsActualResult( createTestCaseUploadForm.getTestStepsActualResult() );
        testCaseUpload.setTestCaseMenuPath( createTestCaseUploadForm.getTestCaseMenuPath() );
        testCaseUpload.setTestStepsAction( createTestCaseUploadForm.getTestStepsAction() );
        testCaseUpload.setTestCaseName( createTestCaseUploadForm.getTestCaseName() );

        return testCaseUpload;
    }

    @Override
    public TestCaseUploadDto fromEntityToTestCaseUploadDto(TestCaseUpload testCaseUpload) {
        if ( testCaseUpload == null ) {
            return null;
        }

        TestCaseUploadDto testCaseUploadDto = new TestCaseUploadDto();

        testCaseUploadDto.setTestStepsAction( testCaseUpload.getTestStepsAction() );
        testCaseUploadDto.setTestStepsData( testCaseUpload.getTestStepsData() );
        testCaseUploadDto.setTestStepsExpectedResult( testCaseUpload.getTestStepsExpectedResult() );
        testCaseUploadDto.setTestCasePrecondition( testCaseUpload.getTestCasePrecondition() );
        testCaseUploadDto.setId( testCaseUpload.getId() );
        testCaseUploadDto.setTestCaseName( testCaseUpload.getTestCaseName() );
        testCaseUploadDto.setProgram( programMapper.fromEntityToAutoCompleteProgramDto( testCaseUpload.getProgram() ) );
        testCaseUploadDto.setTestStepsActualResult( testCaseUpload.getTestStepsActualResult() );
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

    protected Program createTestCaseUploadFormToProgram(CreateTestCaseUploadForm createTestCaseUploadForm) {
        if ( createTestCaseUploadForm == null ) {
            return null;
        }

        Program program = new Program();

        program.setId( createTestCaseUploadForm.getProgramId() );

        return program;
    }
}
