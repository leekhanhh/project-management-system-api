package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testplantestsuiterelation.TestPlanTestSuiteRelationDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.BindingErrorsHandler;
import com.base.meta.exception.NotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public ApiMessageDto<String> createTestPlanTestSuiteRelation(@Valid @RequestBody CreateTestPlanTestSuiteRelationForm createTestPlanTestSuiteRelationForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), ErrorCode.TEST_CASE_ERROR_INVALID);
        }
        if (!isTester()) {
            throw new UnauthorizationException("You are not authorized to perform this action.");
        }

        TestPlan testPlan = testPlanRepository.findById(createTestPlanTestSuiteRelationForm.getTestPlanId()).orElse(null);
        if (testPlan == null) {
            throw new BadRequestException("Test plan not found.", ErrorCode.TEST_PLAN_ERROR_NOT_EXIST);
        }

        List<TestSuite> availableTestSuites = new ArrayList<>();
        StringBuilder message = new StringBuilder();
        for (Long testSuiteId : createTestPlanTestSuiteRelationForm.getTestSuiteIds()) {
            TestSuite testSuite = testSuiteRepository.findById(testSuiteId).orElseThrow(()
                    -> new NotFoundException("Test suite not found.", ErrorCode.TEST_SUITE_ERROR_NOT_EXIST));
            if (testPlanTestSuiteRelationRepository.existsByTestPlanIdAndTestSuiteId(createTestPlanTestSuiteRelationForm.getTestPlanId(), testSuiteId)) {
                message.append("Test plan test suite relation already exists for test plan ").append(testPlan.getId()).append(" and test suite ").append(testSuite.getId()).append(". ");
                throw new BadRequestException(message.toString(), ErrorCode.TEST_PLAN_TEST_SUITE_RELATION_ERROR_EXIST);
            }
            availableTestSuites.add(testSuite);
        }

        for (TestSuite testSuite : availableTestSuites) {
            TestPlanTestSuiteRelation testPlanTestSuiteRelation = new TestPlanTestSuiteRelation();
            testPlanTestSuiteRelation.setTestPlan(testPlan);
            testPlanTestSuiteRelation.setTestSuite(testSuite);
            testPlanTestSuiteRelationRepository.save(testPlanTestSuiteRelation);
        }
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
        criteria.setFlag(BaseMetaConstant.STATUS_ACTIVE);
        Page<TestPlanTestSuiteRelation> testPlanTestSuiteRelationPage = testPlanTestSuiteRelationRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto responseListDto = new ResponseListDto(testPlanTestSuiteRelationMapper.fromDtoToTestPlanTestSuiteRelationDtoList(testPlanTestSuiteRelationPage.getContent()), testPlanTestSuiteRelationPage.getTotalElements(), testPlanTestSuiteRelationPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get test plan test suite relation list success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-by-test-plan", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestSuiteDto>> listTestSuiteByTestPlan(TestPlanTestSuiteRelationCriteria criteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestSuiteDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestPlanTestSuiteRelation> testPlanTestSuiteRelations = testPlanTestSuiteRelationRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto responseListDto = new ResponseListDto(testPlanTestSuiteRelationMapper.fromShortenedEntitiesToTestPlanTestSuiteRelationDtos(testPlanTestSuiteRelations.getContent()), testPlanTestSuiteRelations.getTotalElements(), testPlanTestSuiteRelations.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get test suite list by test plan success.");
        return apiMessageDto;
    }
}
