package com.base.meta.mapper;

import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.dto.testsuitetestcaseralation.TestSuiteTestCaseRelationDto;
import com.base.meta.model.TestCase;
import com.base.meta.model.TestSuiteTestCaseRelation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T18:09:20+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TestSuiteTestCaseRelationMapperImpl implements TestSuiteTestCaseRelationMapper {

    @Autowired
    private TestCaseMapper testCaseMapper;
    @Autowired
    private TestSuiteMapper testSuiteMapper;

    @Override
    public TestSuiteTestCaseRelationDto fromEntityToDto(TestSuiteTestCaseRelation testSuiteTestCaseRelation) {
        if ( testSuiteTestCaseRelation == null ) {
            return null;
        }

        TestSuiteTestCaseRelationDto testSuiteTestCaseRelationDto = new TestSuiteTestCaseRelationDto();

        testSuiteTestCaseRelationDto.setTestSuite( testSuiteMapper.fromEntityToTestSuiteDtoAutoComplete( testSuiteTestCaseRelation.getTestSuite() ) );
        testSuiteTestCaseRelationDto.setTestCase( testCaseMapper.fromEntityToTestCaseDtoAutoComplete( testSuiteTestCaseRelation.getTestCase() ) );

        return testSuiteTestCaseRelationDto;
    }

    @Override
    public List<TestSuiteTestCaseRelationDto> fromEntitiesToDtos(List<TestSuiteTestCaseRelation> testSuiteTestCaseRelations) {
        if ( testSuiteTestCaseRelations == null ) {
            return null;
        }

        List<TestSuiteTestCaseRelationDto> list = new ArrayList<TestSuiteTestCaseRelationDto>( testSuiteTestCaseRelations.size() );
        for ( TestSuiteTestCaseRelation testSuiteTestCaseRelation : testSuiteTestCaseRelations ) {
            list.add( fromEntityToDto( testSuiteTestCaseRelation ) );
        }

        return list;
    }

    @Override
    public TestCaseDto fromShortenedEntityToDto(TestSuiteTestCaseRelation testSuiteTestCaseRelation) {
        if ( testSuiteTestCaseRelation == null ) {
            return null;
        }

        TestCaseDto testCaseDto = new TestCaseDto();

        testCaseDto.setMenuPath( testSuiteTestCaseRelationTestCaseMenuPath( testSuiteTestCaseRelation ) );
        testCaseDto.setName( testSuiteTestCaseRelationTestCaseName( testSuiteTestCaseRelation ) );
        testCaseDto.setPrecondition( testSuiteTestCaseRelationTestCasePrecondition( testSuiteTestCaseRelation ) );
        testCaseDto.setId( testSuiteTestCaseRelationTestCaseId( testSuiteTestCaseRelation ) );
        testCaseDto.setDisplayId( testSuiteTestCaseRelationTestCaseDisplayId( testSuiteTestCaseRelation ) );

        return testCaseDto;
    }

    @Override
    public List<TestCaseDto> fromShortenedEntitiesToDtos(List<TestSuiteTestCaseRelation> testSuiteTestCaseRelations) {
        if ( testSuiteTestCaseRelations == null ) {
            return null;
        }

        List<TestCaseDto> list = new ArrayList<TestCaseDto>( testSuiteTestCaseRelations.size() );
        for ( TestSuiteTestCaseRelation testSuiteTestCaseRelation : testSuiteTestCaseRelations ) {
            list.add( fromShortenedEntityToDto( testSuiteTestCaseRelation ) );
        }

        return list;
    }

    private String testSuiteTestCaseRelationTestCaseMenuPath(TestSuiteTestCaseRelation testSuiteTestCaseRelation) {
        if ( testSuiteTestCaseRelation == null ) {
            return null;
        }
        TestCase testCase = testSuiteTestCaseRelation.getTestCase();
        if ( testCase == null ) {
            return null;
        }
        String menuPath = testCase.getMenuPath();
        if ( menuPath == null ) {
            return null;
        }
        return menuPath;
    }

    private String testSuiteTestCaseRelationTestCaseName(TestSuiteTestCaseRelation testSuiteTestCaseRelation) {
        if ( testSuiteTestCaseRelation == null ) {
            return null;
        }
        TestCase testCase = testSuiteTestCaseRelation.getTestCase();
        if ( testCase == null ) {
            return null;
        }
        String name = testCase.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String testSuiteTestCaseRelationTestCasePrecondition(TestSuiteTestCaseRelation testSuiteTestCaseRelation) {
        if ( testSuiteTestCaseRelation == null ) {
            return null;
        }
        TestCase testCase = testSuiteTestCaseRelation.getTestCase();
        if ( testCase == null ) {
            return null;
        }
        String precondition = testCase.getPrecondition();
        if ( precondition == null ) {
            return null;
        }
        return precondition;
    }

    private Long testSuiteTestCaseRelationTestCaseId(TestSuiteTestCaseRelation testSuiteTestCaseRelation) {
        if ( testSuiteTestCaseRelation == null ) {
            return null;
        }
        TestCase testCase = testSuiteTestCaseRelation.getTestCase();
        if ( testCase == null ) {
            return null;
        }
        Long id = testCase.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String testSuiteTestCaseRelationTestCaseDisplayId(TestSuiteTestCaseRelation testSuiteTestCaseRelation) {
        if ( testSuiteTestCaseRelation == null ) {
            return null;
        }
        TestCase testCase = testSuiteTestCaseRelation.getTestCase();
        if ( testCase == null ) {
            return null;
        }
        String displayId = testCase.getDisplayId();
        if ( displayId == null ) {
            return null;
        }
        return displayId;
    }
}
