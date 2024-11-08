package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testplantestsuiterelation.TestPlanTestSuiteRelationDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testplantestsuiterelation.CreateTestPlanTestSuiteRelationForm;
import com.base.meta.mapper.TestPlanTestSuiteRelationMapper;
import com.base.meta.model.TestPlan;
import com.base.meta.model.TestPlanTestSuiteRelation;
import com.base.meta.model.TestSuite;
import com.base.meta.model.criteria.TestPlanTestSuiteRelationCriteria;
import com.base.meta.repository.TestPlanRepository;
import com.base.meta.repository.TestPlanTestSuiteRelationRepository;
import com.base.meta.repository.TestSuiteRepository;
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
@RequestMapping("/v1/test-plan-test-suite-relation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestPlanTestSuiteRelationController extends ABasicController {
    @Autowired
    TestPlanTestSuiteRelationRepository testPlanTestSuiteRelationRepository;
    @Autowired
    TestPlanTestSuiteRelationMapper testPlanTestSuiteRelationMapper;
    @Autowired
    TestPlanRepository testPlanRepository;
    @Autowired
    TestSuiteRepository testSuiteRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TPTSR_C')")
    public ApiMessageDto<TestPlanTestSuiteRelationDto> createTestPlanTestSuiteRelation(@Valid @RequestBody CreateTestPlanTestSuiteRelationForm createTestPlanTestSuiteRelationForm, BindingResult bindingResult) {
        ApiMessageDto<TestPlanTestSuiteRelationDto> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("You are not authorized to perform this action.");
        }

        TestPlan testPlan = testPlanRepository.findById(createTestPlanTestSuiteRelationForm.getTestPlanId()).orElse(null);
        if (testPlan == null) {
            throw new BadRequestException("Test plan not found.", ErrorCode.TEST_PLAN_ERROR_NOT_EXIST);
        }

        TestSuite testSuite = testSuiteRepository.findById(createTestPlanTestSuiteRelationForm.getTestSuiteId()).orElse(null);
        if (testSuite == null) {
            throw new BadRequestException("Test suite not found.", ErrorCode.TEST_SUITE_ERROR_NOT_EXIST);
        }

        TestPlanTestSuiteRelation testPlanTestSuiteRelation = testPlanTestSuiteRelationMapper.fromCreateTestPlanTestSuiteRelationFormToEntity(createTestPlanTestSuiteRelationForm);
        testPlanTestSuiteRelation.setTestPlan(testPlan);
        testPlanTestSuiteRelation.setTestSuite(testSuite);
        testPlanTestSuiteRelationRepository.save(testPlanTestSuiteRelation);
        apiMessageDto.setData(testPlanTestSuiteRelationMapper.fromEntityToTestPlanTestSuiteRelationDto(testPlanTestSuiteRelation));
        apiMessageDto.setMessage("Create test plan test suite relation success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TPTSR_D')")
    public ApiMessageDto<String> deleteTestPlanTestSuiteRelation(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("You are not authorized to perform this action.");
        }

        TestPlanTestSuiteRelation testPlanTestSuiteRelation = testPlanTestSuiteRelationRepository.findById(id).orElse(null);
        if (testPlanTestSuiteRelation == null) {
            throw new BadRequestException("Test plan test suite relation not found.", ErrorCode.TEST_PLAN_TEST_SUITE_RELATION_ERROR_NOT_EXIST);
        }

        testPlanTestSuiteRelationRepository.delete(testPlanTestSuiteRelation);
        apiMessageDto.setMessage("Delete test plan test suite relation success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestPlanTestSuiteRelationDto> getTestPlanTestSuiteRelation(@PathVariable Long id) {
        ApiMessageDto<TestPlanTestSuiteRelationDto> apiMessageDto = new ApiMessageDto<>();
        TestPlanTestSuiteRelation testPlanTestSuiteRelation = testPlanTestSuiteRelationRepository.findById(id).orElse(null);
        if (testPlanTestSuiteRelation == null) {
            throw new BadRequestException("Test plan test suite relation not found.", ErrorCode.TEST_PLAN_TEST_SUITE_RELATION_ERROR_NOT_EXIST);
        }
        apiMessageDto.setData(testPlanTestSuiteRelationMapper.fromEntityToTestPlanTestSuiteRelationDto(testPlanTestSuiteRelation));
        apiMessageDto.setMessage("Get test plan test suite relation success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestPlanTestSuiteRelationDto>> listTestPlanTestSuiteRelation(TestPlanTestSuiteRelationCriteria criteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestPlanTestSuiteRelationDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestPlanTestSuiteRelation> testPlanTestSuiteRelationPage = testPlanTestSuiteRelationRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto<TestPlanTestSuiteRelationDto> responseListDto = new ResponseListDto(testPlanTestSuiteRelationMapper.fromDtoToTestPlanTestSuiteRelationDtoList(testPlanTestSuiteRelationPage.getContent()), testPlanTestSuiteRelationPage.getTotalElements(), testPlanTestSuiteRelationPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get test plan test suite relation list success.");
        return apiMessageDto;
    }
}
