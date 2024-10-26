package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testsuitetestcaseralation.TestSuiteTestCaseRelationDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testsuitetestcaserelation.CreateTestSuiteTestCaseRelationForm;
import com.base.meta.mapper.TestSuiteTestCaseRelationMapper;
import com.base.meta.model.TestCase;
import com.base.meta.model.TestSuite;
import com.base.meta.model.TestSuiteTestCaseRelation;
import com.base.meta.model.criteria.TestSuiteTestCaseRelationCriteria;
import com.base.meta.repository.TestCaseRepository;
import com.base.meta.repository.TestSuiteRepository;
import com.base.meta.repository.TestSuiteTestCaseRelationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/test-suite-test-case-relation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestSuiteTestCaseRelationController extends ABasicController {
    @Autowired
    TestSuiteTestCaseRelationRepository testSuiteTestCaseRelationRepository;
    @Autowired
    TestSuiteTestCaseRelationMapper testSuiteTestCaseRelationMapper;
    @Autowired
    TestSuiteRepository testSuiteRepository;
    @Autowired
    TestCaseRepository testCaseRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSTCR_C')")
    public ApiMessageDto<String> createTestSuiteTestCaseRelation(@Valid @RequestBody CreateTestSuiteTestCaseRelationForm createTestSuiteTestCaseRelationForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("You are not authorized to perform this action.");
        }

        TestSuite testSuite = testSuiteRepository.findById(createTestSuiteTestCaseRelationForm.getTestSuiteId()).orElse(null);
        if (testSuite == null) {
            throw new BadRequestException("Test suite not found.", ErrorCode.TEST_SUITE_ERROR_NOT_EXIST);
        }

        TestCase testCase = testCaseRepository.findById(createTestSuiteTestCaseRelationForm.getTestCaseId()).orElse(null);
        if (testCase == null) {
            throw new BadRequestException("Test case not found.", ErrorCode.TEST_CASE_ERROR_NOT_EXIST);
        }

        TestSuiteTestCaseRelation testSuiteTestCaseRelation = testSuiteTestCaseRelationRepository.findByTestSuiteIdAndTestCaseId(createTestSuiteTestCaseRelationForm.getTestSuiteId(), createTestSuiteTestCaseRelationForm.getTestCaseId());
        if (testSuiteTestCaseRelation != null) {
            throw new BadRequestException("Test suite and test case already exist.", ErrorCode.TEST_SUITE_TEST_CASE_RELATION_ERROR_EXIST);
        }

        testSuiteTestCaseRelation.setTestSuite(testSuite);
        testSuiteTestCaseRelation.setTestCase(testCase);
        testSuiteTestCaseRelationRepository.save(testSuiteTestCaseRelation);

        apiMessageDto.setMessage("Create test suite test case relation success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSTCR_D')")
    public ApiMessageDto<String> deleteTestSuiteTestCaseRelation(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("You are not authorized to perform this action.");
        }

        TestSuiteTestCaseRelation testSuiteTestCaseRelation = testSuiteTestCaseRelationRepository.findById(id).orElse(null);
        if (testSuiteTestCaseRelation == null) {
            throw new BadRequestException("Test suite test case relation not found.", ErrorCode.TEST_SUITE_TEST_CASE_RELATION_ERROR_EXIST);
        }
        testSuiteTestCaseRelationRepository.delete(testSuiteTestCaseRelation);

        apiMessageDto.setMessage("Delete test suite test case relation success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestSuiteTestCaseRelationDto>> getTestSuiteTestCaseRelationList(TestSuiteTestCaseRelationCriteria testSuiteTestCaseRelationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestSuiteTestCaseRelationDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestSuiteTestCaseRelation> testSuiteTestCaseRelationPage = testSuiteTestCaseRelationRepository.findAll(testSuiteTestCaseRelationCriteria.getSpecification(), pageable);
        ResponseListDto<TestSuiteTestCaseRelationDto> responseListDto = new ResponseListDto(testSuiteTestCaseRelationMapper.fromEntitiesToDtos(testSuiteTestCaseRelationPage.getContent()), testSuiteTestCaseRelationPage.getTotalElements(), testSuiteTestCaseRelationPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get test suite test case relation list success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestSuiteTestCaseRelationDto> getTestSuiteTestCaseRelation(@PathVariable("id") Long id) {
        ApiMessageDto<TestSuiteTestCaseRelationDto> apiMessageDto = new ApiMessageDto<>();
        TestSuiteTestCaseRelation testSuiteTestCaseRelation = testSuiteTestCaseRelationRepository.findById(id).orElse(null);
        if (testSuiteTestCaseRelation == null) {
            throw new BadRequestException("Test suite test case relation not found.", ErrorCode.TEST_SUITE_TEST_CASE_RELATION_ERROR_EXIST);
        }
        apiMessageDto.setData(testSuiteTestCaseRelationMapper.fromEntityToDto(testSuiteTestCaseRelation));
        apiMessageDto.setMessage("Get test suite test case relation success.");
        return apiMessageDto;
    }

}
