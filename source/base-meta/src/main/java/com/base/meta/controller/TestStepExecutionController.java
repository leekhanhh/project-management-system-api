package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.teststepexecution.TestStepExecutionDto;
import com.base.meta.exception.NotFoundException;
import com.base.meta.form.teststepexecution.CreateTestStepExecutionForm;
import com.base.meta.form.teststepexecution.UpdateTestStepExecutionForm;
import com.base.meta.mapper.TestStepExecutionMapper;
import com.base.meta.model.*;
import com.base.meta.model.criteria.TestStepExecutionCriteria;
import com.base.meta.repository.*;
import com.base.meta.service.BaseMetaApiService;
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
import java.util.Date;

@RestController
@RequestMapping("/v1/test-step-execution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestStepExecutionController extends ABasicController{
    private static final String PREFIX_ENTITY = "TSTE";
    @Autowired
    TestStepExecutionRepository testStepExecutionRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TestStepRepository testStepRepository;
    @Autowired
    TestCaseExecutionRepository testCaseExecutionRepository;
    @Autowired
    TestStepExecutionMapper testStepExecutionMapper;
    @Autowired
    BaseMetaApiService baseMetaApiService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSTE_C')")
    public ApiMessageDto<String> createTestStepExecution(@Valid @RequestBody CreateTestStepExecutionForm createTestStepExecutionForm, BindingResult bindingResult) {
        TestStep testStep = testStepRepository.findFirstById(createTestStepExecutionForm.getTestStepId()).orElseThrow(
                () -> new NotFoundException("Test step not found!", ErrorCode.TEST_STEP_ERROR_NOT_EXIST)
        );
        TestCaseExecution testCaseExecution = testCaseExecutionRepository.findFirstById(createTestStepExecutionForm.getTestCaseExecutionId()).orElseThrow(
                () -> new NotFoundException("Test case execution not found!", ErrorCode.TEST_CASE_EXECUTION_ERROR_NOT_EXIST)
        );
        Category category = categoryRepository.findFirstById(createTestStepExecutionForm.getCategoryId());
        if (category == null){
            throw new NotFoundException("Category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        TestStepExecution testStepExecution = new TestStepExecution();
        testStepExecution.setTestStep(testStep);
        testStepExecution.setTestCaseExecution(testCaseExecution);
        testStepExecution.setStatus(category);
        testCaseExecution.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        testStepExecutionRepository.save(testStepExecution);

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Create a new Test Step Execution successfully.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSTE_U')")
    public ApiMessageDto<String> updateTestStepExecution(@Valid @RequestBody UpdateTestStepExecutionForm updateTestStepExecutionForm, BindingResult bindingResult) {
        TestStepExecution testStepExecution = testStepExecutionRepository.findFirstById(updateTestStepExecutionForm.getId()).orElseThrow(
                () -> new NotFoundException("Test Step Execution not found!", ErrorCode.TEST_STEP_EXECUTION_ERROR_NOT_EXIST)
        );
        Category category = categoryRepository.findFirstById(updateTestStepExecutionForm.getCategoryId());
        if (category == null){
            throw new NotFoundException("Category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        testStepExecution.setStatus(category);
        testStepExecutionRepository.save(testStepExecution);

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Update a Test Step Execution successfully.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestStepExecutionDto> getTestStepExecution(@PathVariable Long id) {
        TestStepExecution testStepExecution = testStepExecutionRepository.findFirstById(id).orElseThrow(
                () -> new NotFoundException("Test Step Execution not found!", ErrorCode.TEST_STEP_EXECUTION_ERROR_NOT_EXIST)
        );
        ApiMessageDto<TestStepExecutionDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(testStepExecutionMapper.fromEntityToDto(testStepExecution));
        apiMessageDto.setMessage("Get a Test Step Execution successfully.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestStepExecutionDto>> listTestStepExecution(TestStepExecutionCriteria criteria, Pageable pageable) {
        Page<TestStepExecution> page = testStepExecutionRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto<TestStepExecutionDto> responseListDto = new ResponseListDto(testStepExecutionMapper.fromEntityListToDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        ApiMessageDto<ResponseListDto<TestStepExecutionDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List Test Step Execution successfully.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSTE_D')")
    public ApiMessageDto<String> deleteTestStepExecution(@PathVariable Long id) {
        TestStepExecution testStepExecution = testStepExecutionRepository.findFirstById(id).orElseThrow(
                () -> new NotFoundException("Test Step Execution not found!", ErrorCode.TEST_STEP_EXECUTION_ERROR_NOT_EXIST)
        );
        testStepExecutionRepository.delete(testStepExecution);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Delete a Test Step Execution successfully.");
        return apiMessageDto;
    }
}
