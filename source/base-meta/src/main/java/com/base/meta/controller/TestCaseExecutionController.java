package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testcaseexecution.CreateTestCaseExecutionForm;
import com.base.meta.mapper.TestCaseExecutionMapper;
import com.base.meta.model.*;
import com.base.meta.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/test-case-execution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestCaseExecutionController extends ABasicController {
    @Autowired
    TestCaseExecutionRepository testCaseExecutionRepository;
    @Autowired
    TestCaseExecutionMapper testCaseExecutionMapper;
    @Autowired
    TestExecutionTurnRepository testExecutionTurnRepository;
    @Autowired
    TestCaseRepository testCaseRepository;
    @Autowired
    TestSuiteRepository testSuiteRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TCE_C')")
    public ApiMessageDto<String> createTestCaseExecution(@Valid @RequestBody CreateTestCaseExecutionForm createTestCaseExecutionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed create!");
        }
        TestExecutionTurn testExecutionTurn = testExecutionTurnRepository.findById(createTestCaseExecutionForm.getTestExecutionTurnId()).orElseThrow(()
                -> new NotFoundException("Test execution turn not found!", ErrorCode.TEST_EXECUTION_TURN_ERROR_NOT_EXIST));

        TestCase testCase = testCaseRepository.findById(createTestCaseExecutionForm.getTestCaseId()).orElseThrow(()
                -> new NotFoundException("Test case not found!", ErrorCode.TEST_CASE_ERROR_NOT_EXIST));

        TestSuite testSuite = testSuiteRepository.findById(createTestCaseExecutionForm.getTestSuiteId()).orElseThrow(()
                -> new NotFoundException("Test suite not found!", ErrorCode.TEST_SUITE_ERROR_NOT_EXIST));

        Category status = categoryRepository.findById(createTestCaseExecutionForm.getStatusId()).orElseThrow(()
                -> new NotFoundException("Status not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        Category testExecutionTypeCode = categoryRepository.findById(createTestCaseExecutionForm.getTestExecutionTypeCodeId()).orElseThrow(()
                -> new NotFoundException("Test execution type code not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        TestCaseExecution testCaseExecution = new TestCaseExecution();
        testCaseExecution.setTestExecutionTurn(testExecutionTurn);
        testCaseExecution.setTestCase(testCase);
        testCaseExecution.setTestSuite(testSuite);
        testCaseExecution.setStatus(status);
        testCaseExecution.setTestExecutionTypeCode(testExecutionTypeCode);
        testCaseExecutionRepository.save(testCaseExecution);
        apiMessageDto.setMessage("Create test case execution successfully!");
        return apiMessageDto;
    }
}
