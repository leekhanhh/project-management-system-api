package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testsuiteexecution.CreateTestSuiteExecutionForm;
import com.base.meta.form.testsuiteexecution.UpdateTestSuiteExecutionForm;
import com.base.meta.mapper.TestSuiteExecutionMapper;
import com.base.meta.model.Category;
import com.base.meta.model.TestExecutionTurn;
import com.base.meta.model.TestSuite;
import com.base.meta.model.TestSuiteExecution;
import com.base.meta.repository.CategoryRepository;
import com.base.meta.repository.TestExecutionTurnRepository;
import com.base.meta.repository.TestSuiteExecutionRepository;
import com.base.meta.repository.TestSuiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/test-suite-execution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestSuiteExecutionController extends ABasicController {
    @Autowired
    TestSuiteExecutionRepository testSuiteExecutionRepository;
    @Autowired
    TestSuiteExecutionMapper testSuiteExecutionMapper;
    @Autowired
    TestSuiteRepository testSuiteRepository;
    @Autowired
    TestExecutionTurnRepository testExecutionTurnRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSUE_C')")
    public ApiMessageDto<String> createTestSuiteExecution(@Valid @RequestBody CreateTestSuiteExecutionForm createTestSuiteExecutionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed create!");
        }

        TestSuite testSuite = testSuiteRepository.findById(createTestSuiteExecutionForm.getTestSuiteId()).orElseThrow(()
                -> new NotFoundException("Test suite not found.", ErrorCode.TEST_SUITE_ERROR_NOT_EXIST));

        TestExecutionTurn testExecutionTurn = testExecutionTurnRepository.findById(createTestSuiteExecutionForm.getTestExecutionTurnId()).orElseThrow(()
                -> new NotFoundException("Test execution turn not found.", ErrorCode.TEST_EXECUTION_TURN_ERROR_NOT_EXIST));

        Category category = categoryRepository.findById(createTestSuiteExecutionForm.getStatusId()).orElseThrow(()
                -> new NotFoundException("Category not found.", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        if (testSuiteExecutionRepository.findFirstByTestSuiteAndOrderNumber(createTestSuiteExecutionForm.getTestSuiteId(), createTestSuiteExecutionForm.getOrderNumber()) != null) {
            throw new NotFoundException("Order number is existed.", ErrorCode.TEST_SUITE_EXECUTION_ERROR_ORDER_NUMBER_EXISTED);
        }
        TestSuiteExecution testSuiteExecution = testSuiteExecutionMapper.fromCreateTestSuiteExecutionFormToEntity(createTestSuiteExecutionForm);
        testSuiteExecution.setTestSuite(testSuite);
        testSuiteExecution.setTestExecutionTurn(testExecutionTurn);
        testSuiteExecution.setStatus(category);
        testSuiteExecutionRepository.save(testSuiteExecution);

        apiMessageDto.setMessage("Test suite execution created successfully.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSUE_U')")
    public ApiMessageDto<String> updateTestSuiteExecution(@Valid @RequestBody UpdateTestSuiteExecutionForm updateTestSuiteExecutionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed update!");
        }
        TestSuiteExecution testSuiteExecution = testSuiteExecutionRepository.findById(updateTestSuiteExecutionForm.getId()).orElseThrow(()
                -> new NotFoundException("Test suite execution not found.", ErrorCode.TEST_SUITE_EXECUTION_ERROR_NOT_EXIST));

        if (updateTestSuiteExecutionForm.getOrderNumber() != null && !updateTestSuiteExecutionForm.getOrderNumber().equals(testSuiteExecution.getOrderNumber())) {
            if (testSuiteExecutionRepository.findFirstByTestSuiteAndOrderNumber(testSuiteExecution.getTestSuite().getId(), updateTestSuiteExecutionForm.getOrderNumber()) != null) {
                throw new NotFoundException("Order number is existed.", ErrorCode.TEST_SUITE_EXECUTION_ERROR_ORDER_NUMBER_EXISTED);
            }
        }
        Category status = categoryRepository.findById(updateTestSuiteExecutionForm.getStatusId()).orElseThrow(()
                -> new NotFoundException("Category not found.", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        testSuiteExecutionMapper.updateTestSuiteExecutionFromEntity(updateTestSuiteExecutionForm, testSuiteExecution);
        testSuiteExecution.setStatus(status);
        testSuiteExecutionRepository.save(testSuiteExecution);

        apiMessageDto.setMessage("Test suite execution updated successfully.");
        return apiMessageDto;
    }


}
