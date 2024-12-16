package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testsuiteexecution.TestSuiteExecutionDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.ModifyFlagForm;
import com.base.meta.form.testsuiteexecution.CreateTestSuiteExecutionForm;
import com.base.meta.form.testsuiteexecution.UpdateTestSuiteExecutionForm;
import com.base.meta.mapper.TestSuiteExecutionMapper;
import com.base.meta.model.Category;
import com.base.meta.model.TestExecutionTurn;
import com.base.meta.model.TestSuite;
import com.base.meta.model.TestSuiteExecution;
import com.base.meta.model.criteria.TestSuiteExecutionCriteria;
import com.base.meta.repository.CategoryRepository;
import com.base.meta.repository.TestExecutionTurnRepository;
import com.base.meta.repository.TestSuiteExecutionRepository;
import com.base.meta.repository.TestSuiteRepository;
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
@RequestMapping("/v1/test-suite-execution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestSuiteExecutionController extends ABasicController {
    private static final String PREFIX_ENTITY = "TSUE";
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
    @Autowired
    BaseMetaApiService baseMetaApiService;

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

        if (Boolean.TRUE.equals(testSuiteExecutionRepository.existsByTestSuiteAndOrderNumber(testSuite.getId(), createTestSuiteExecutionForm.getOrderNumber()))) {
            throw new BadRequestException("Order number is existed.", ErrorCode.TEST_SUITE_EXECUTION_ERROR_ORDER_NUMBER_EXISTED);
        }

        TestSuiteExecution testSuiteExecution = new TestSuiteExecution();
        testSuiteExecution.setOrderNumber(createTestSuiteExecutionForm.getOrderNumber());
        testSuiteExecution.setTestSuite(testSuite);
        testSuiteExecution.setTestExecutionTurn(testExecutionTurn);
        testSuiteExecution.setStatus(category);
        testSuiteExecution.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
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
            if(Boolean.TRUE.equals(testSuiteExecutionRepository.existsByTestSuiteAndOrderNumber(testSuiteExecution.getTestSuite().getId(), updateTestSuiteExecutionForm.getOrderNumber()))){
                throw new BadRequestException("Order number is existed.", ErrorCode.TEST_SUITE_EXECUTION_ERROR_ORDER_NUMBER_EXISTED);
            }
        }
        Category status = categoryRepository.findById(updateTestSuiteExecutionForm.getStatusId()).orElseThrow(()
                -> new NotFoundException("Category not found.", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        testSuiteExecution.setOrderNumber(updateTestSuiteExecutionForm.getOrderNumber());
        testSuiteExecution.setStatus(status);
        testSuiteExecutionRepository.save(testSuiteExecution);

        apiMessageDto.setMessage("Test suite execution updated successfully.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSUE_D')")
    public ApiMessageDto<String> deleteTestSuiteExecution(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        TestSuiteExecution testSuiteExecution = testSuiteExecutionRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Test suite execution not found.", ErrorCode.TEST_SUITE_EXECUTION_ERROR_NOT_EXIST));
        testSuiteExecutionRepository.delete(testSuiteExecution);
        apiMessageDto.setMessage("Test suite execution deleted successfully.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestSuiteExecutionDto> getTestSuiteExecution(@PathVariable Long id) {
        ApiMessageDto<TestSuiteExecutionDto> apiMessageDto = new ApiMessageDto<>();
        TestSuiteExecution testSuiteExecution = testSuiteExecutionRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Test suite execution not found.", ErrorCode.TEST_SUITE_EXECUTION_ERROR_NOT_EXIST));
        TestSuiteExecutionDto testSuiteExecutionDto = testSuiteExecutionMapper.fromEntityToTestSuiteExecutionDto(testSuiteExecution);
        apiMessageDto.setData(testSuiteExecutionDto);
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestSuiteExecutionDto>> listTestSuiteExecution(TestSuiteExecutionCriteria testSuiteExecutionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestSuiteExecutionDto>> apiMessageDto = new ApiMessageDto<>();
        testSuiteExecutionCriteria.setFlag(BaseMetaConstant.STATUS_ACTIVE);
        Page<TestSuiteExecution> testSuiteExecutionPage = testSuiteExecutionRepository.findAll(testSuiteExecutionCriteria.getSpecification(), pageable);
        ResponseListDto<TestSuiteExecutionDto> responseListDto = new ResponseListDto(testSuiteExecutionMapper.fromEntityToTestSuiteExecutionDtoList(testSuiteExecutionPage.getContent()), testSuiteExecutionPage.getTotalElements(), testSuiteExecutionPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @PutMapping(value = "/update-flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> updateFlagTestSuiteExecution(@RequestBody ModifyFlagForm modifyFlagForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed update!");
        }
        TestSuiteExecution testSuiteExecution = testSuiteExecutionRepository.findById(modifyFlagForm.getObjectId()).orElseThrow(()
                -> new NotFoundException("Test suite execution not found!", ErrorCode.TEST_SUITE_EXECUTION_ERROR_NOT_EXIST));
        testSuiteExecution.setFlag(modifyFlagForm.getFlag());
        testSuiteExecutionRepository.save(testSuiteExecution);
        apiMessageDto.setMessage("Update flag test suite execution successfully!");
        return apiMessageDto;
    }

}
