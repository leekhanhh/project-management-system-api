package com.base.meta.mapper;

import com.base.meta.dto.testdefectcomment.TestDefectCommentDto;
import com.base.meta.form.testdefectcomment.CreateTestDefectCommentForm;
import com.base.meta.form.testdefectcomment.UpdateTestDefectCommentForm;
import com.base.meta.model.TestDefectComment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-06T17:26:55+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestDefectCommentMapperImpl implements TestDefectCommentMapper {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TestDefectMapper testDefectMapper;

    @Override
    public TestDefectComment fromCreateTestDefectCommentFormToEntity(CreateTestDefectCommentForm createTestDefectCommentForm) {
        if ( createTestDefectCommentForm == null ) {
            return null;
        }

        TestDefectComment testDefectComment = new TestDefectComment();

        testDefectComment.setComment( createTestDefectCommentForm.getComment() );
        testDefectComment.setTitle( createTestDefectCommentForm.getTitle() );

        return testDefectComment;
    }

    @Override
    public void fromUpdateTestDefectCommentFormToEntity(UpdateTestDefectCommentForm updateTestDefectCommentForm, TestDefectComment testDefectComment) {
        if ( updateTestDefectCommentForm == null ) {
            return;
        }

        if ( updateTestDefectCommentForm.getComment() != null ) {
            testDefectComment.setComment( updateTestDefectCommentForm.getComment() );
        }
    }

    @Override
    public TestDefectCommentDto fromEntityToDto(TestDefectComment testDefectComment) {
        if ( testDefectComment == null ) {
            return null;
        }

        TestDefectCommentDto testDefectCommentDto = new TestDefectCommentDto();

        testDefectCommentDto.setSender( accountMapper.fromAccountToDto( testDefectComment.getSender() ) );
        testDefectCommentDto.setTestDefect( testDefectMapper.fromEntityToDtoAutoComplete( testDefectComment.getTestDefect() ) );
        testDefectCommentDto.setComment( testDefectComment.getComment() );
        testDefectCommentDto.setId( testDefectComment.getId() );
        testDefectCommentDto.setTitle( testDefectComment.getTitle() );

        return testDefectCommentDto;
    }

    @Override
    public List<TestDefectCommentDto> fromEntityToDtoList(List<TestDefectComment> testDefectComments) {
        if ( testDefectComments == null ) {
            return null;
        }

        List<TestDefectCommentDto> list = new ArrayList<TestDefectCommentDto>( testDefectComments.size() );
        for ( TestDefectComment testDefectComment : testDefectComments ) {
            list.add( fromEntityToDto( testDefectComment ) );
        }

        return list;
    }
}
