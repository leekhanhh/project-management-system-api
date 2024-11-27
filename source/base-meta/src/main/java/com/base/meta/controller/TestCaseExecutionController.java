package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testcaseexecution.TestCaseExecutionDto;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.ModifyFlagForm;
import com.base.meta.form.testcaseexecution.CreateTestCaseExecutionForm;
import com.base.meta.form.testcaseexecution.UpdateTestCaseExecutionForm;
import com.base.meta.mapper.TestCaseExecutionMapper;
import com.base.meta.model.*;
import com.base.meta.model.criteria.TestCaseExecutionCriteria;
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
@RequestMapping("/v1/test-case-execution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestCaseExecutionController extends ABasicController {
    private static final String PREFIX_ENTITY = "TCE";
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
    @Autowired
    BaseMetaApiService baseMetaApiService;

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
        testCaseExecution.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        testCaseExecutionRepository.save(testCaseExecution);
        apiMessageDto.setMessage("Create test case execution successfully!");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TCE_U')")
    public ApiMessageDto<String> updateTestCaseExecution(@Valid @RequestBody UpdateTestCaseExecutionForm updateTestCaseExecutionForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allowed update!");
        }

        TestCaseExecution testCaseExecution = testCaseExecutionRepository.findById(updateTestCaseExecutionForm.getId()).orElseThrow(()
                -> new NotFoundException("Test case execution not found!", ErrorCode.TEST_CASE_EXECUTION_ERROR_NOT_EXIST));

        Category status = categoryRepository.findById(updateTestCaseExecutionForm.getStatusId()).orElseThrow(()
                -> new NotFoundException("Status not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        Category testExecutionTypeCode = categoryRepository.findById(updateTestCaseExecutionForm.getTestExecutionTypeCodeId()).orElseThrow(()
                -> new NotFoundException("Test execution type code not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        testCaseExecution.setStatus(status);
        testCaseExecution.setTestExecutionTypeCode(testExecutionTypeCode);
        testCaseExecutionRepository.save(testCaseExecution);
        apiMessageDto.setMessage("Update test case execution successfully!");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TCE_D')")
    public ApiMessageDto<String> deleteTestCaseExecution(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        TestCaseExecution testCaseExecution = testCaseExecutionRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Test case execution not found!", ErrorCode.TEST_CASE_EXECUTION_ERROR_NOT_EXIST));
        testCaseExecutionRepository.delete(testCaseExecution);
        apiMessageDto.setMessage("Delete test case execution successfully!");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestCaseExecutionDto> getTestCaseExecution(@PathVariable Long id) {
        TestCaseExecution testCaseExecution = testCaseExecutionRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Test case execution not found!", ErrorCode.TEST_CASE_EXECUTION_ERROR_NOT_EXIST));
        ApiMessageDto<TestCaseExecutionDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(testCaseExecutionMapper.fromEntityToTestCaseExecutionDto(testCaseExecution));
        apiMessageDto.setMessage("Get test case execution successfully!");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestCaseExecutionDto>> listTestCaseExecution(TestCaseExecutionCriteria testCaseExecutionCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<TestCaseExecutionDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestCaseExecution> testCaseExecutionPage = testCaseExecutionRepository.findAll(testCaseExecutionCriteria.getSpecification(), pageable);
        ResponseListDto<TestCaseExecutionDto> responseListDto = new ResponseListDto(testCaseExecutionMapper.fromEntityToTestCaseExecutionDtoList(
                testCaseExecutionPage.getContent()),
                testCaseExecutionPage.getTotalElements(),
                testCaseExecutionPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List test case execution successfully!");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TCE_UF')")
    public ApiMessageDto<String> updateFlagTestCaseExecution(@RequestBody ModifyFlagForm modifyFlagForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed update flag!");
        }
        TestCaseExecution testCaseExecution = testCaseExecutionRepository.findById(modifyFlagForm.getObjectId()).orElseThrow(()
                -> new NotFoundException("Test case execution not found!", ErrorCode.TEST_CASE_EXECUTION_ERROR_NOT_EXIST));
        testCaseExecution.setFlag(modifyFlagForm.getFlag());
        testCaseExecutionRepository.save(testCaseExecution);
        apiMessageDto.setMessage("Update flag test case execution successfully!");
        return apiMessageDto;
    }
}
