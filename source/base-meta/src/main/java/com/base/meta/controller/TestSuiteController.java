package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testsuite.TestSuiteDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.ModifyFlagForm;
import com.base.meta.form.testsuite.CreateTestSuiteForm;
import com.base.meta.form.testsuite.UpdateTestSuiteForm;
import com.base.meta.mapper.TestSuiteMapper;
import com.base.meta.model.Account;
import com.base.meta.model.TestPlan;
import com.base.meta.model.TestPlanTestSuiteRelation;
import com.base.meta.model.TestSuite;
import com.base.meta.model.criteria.TestSuiteCriteria;
import com.base.meta.repository.*;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/test-suite")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestSuiteController extends ABasicController{
    @Autowired
    TestSuiteRepository testSuiteRepository;
    @Autowired
    TestSuiteMapper testSuiteMapper;
    @Autowired
    TestPlanRepository testPlanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TestSuiteTestCaseRelationRepository testSuiteTestCaseRelationRepository;
    @Autowired
    TestPlanTestSuiteRelationRepository testPlanTestSuiteRelationRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSU_C')")
    public ApiMessageDto<String> createTestSuite(@Valid @RequestBody CreateTestSuiteForm createTestSuiteForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed create!");
        }

        Account account = accountRepository.findById(createTestSuiteForm.getAccountId()).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }

        TestPlan testPlan = testPlanRepository.findById(createTestSuiteForm.getTestPlanId()).orElse(null);
        if (testPlan == null) {
            throw new BadRequestException("Test plan is not existed!", ErrorCode.TEST_PLAN_ERROR_NOT_EXIST);
        }

        TestSuite testSuite = testSuiteMapper.fromCreateTestSuiteFormToEntity(createTestSuiteForm);
        testSuite.setTestPlan(testPlan);
        testSuite.setAccount(account);
        TestPlanTestSuiteRelation testPlanTestSuiteRelation = new TestPlanTestSuiteRelation();
        testPlanTestSuiteRelation.setTestPlan(testPlan);
        testPlanTestSuiteRelation.setTestSuite(testSuite);
        testSuiteRepository.save(testSuite);
        testPlanTestSuiteRelationRepository.save(testPlanTestSuiteRelation);
        apiMessageDto.setMessage("Create test suite success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSU_U')")
    public ApiMessageDto<String> updateTestSuite(@Valid @RequestBody UpdateTestSuiteForm updateTestSuiteForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed update!");
        }
        TestSuite testSuite = testSuiteRepository.findById(updateTestSuiteForm.getId()).orElse(null);
        if (testSuite == null) {
            throw new BadRequestException("Test suite is not existed!", ErrorCode.TEST_SUITE_ERROR_NOT_EXIST);
        }

        Account account = accountRepository.findById(updateTestSuiteForm.getAccountId()).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }

        TestPlan testPlan = testPlanRepository.findById(updateTestSuiteForm.getTestPlanId()).orElse(null);
        if (testPlan == null) {
            throw new BadRequestException("Test plan is not existed!", ErrorCode.TEST_PLAN_ERROR_NOT_EXIST);
        }

        testSuiteMapper.updateTestSuiteFromEntity(updateTestSuiteForm, testSuite);
        testSuite.setAccount(account);
        testSuiteRepository.save(testSuite);
        apiMessageDto.setMessage("Update test suite success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSU_D')")
    public ApiMessageDto<String> deleteTestSuite(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        TestSuite testSuite = testSuiteRepository.findById(id).orElse(null);
        if (testSuite == null) {
            throw new BadRequestException("Test suite is not existed!", ErrorCode.TEST_SUITE_ERROR_NOT_EXIST);
        }
        testSuiteRepository.delete(testSuite);
        apiMessageDto.setMessage("Delete test suite success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestSuiteDto> getTestSuite(@PathVariable("id") Long id) {
        ApiMessageDto<TestSuiteDto> apiMessageDto = new ApiMessageDto<>();
        TestSuite testSuite = testSuiteRepository.findById(id).orElse(null);
        if (testSuite == null) {
            throw new BadRequestException("Test suite is not existed!", ErrorCode.TEST_SUITE_ERROR_NOT_EXIST);
        }
        TestSuiteDto testSuiteDto = testSuiteMapper.fromEntityToTestSuiteDto(testSuite);
        Integer testCaseCount = testSuiteTestCaseRelationRepository.countByTestSuiteId(id);
        testSuiteDto.setTestCaseCount(testCaseCount);
        apiMessageDto.setData(testSuiteDto);
        apiMessageDto.setMessage("Get test suite success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestSuiteDto>> listTestSuites(TestSuiteCriteria testSuiteCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestSuiteDto>> apiMessageDto = new ApiMessageDto<>();

        Page<TestSuiteDto> testSuiteDtoPage = testSuiteRepository
                .findAll(testSuiteCriteria.getSpecification(), pageable)
                .map(testSuiteMapper::fromEntityToTestSuiteDto);

        List<Long> testSuiteIds = testSuiteDtoPage
                .getContent()
                .stream()
                .map(TestSuiteDto::getId)
                .collect(Collectors.toList());

        List<Object[]> testCaseCountList = testSuiteTestCaseRelationRepository
                .countTestSuiteTestCaseRelationsByTestSuiteIds(testSuiteIds);

        Map<Long, Integer> testCaseCounts = testCaseCountList.stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> ((Number) result[1]).intValue()
                ));

        testSuiteDtoPage.getContent().forEach(testSuiteDto -> {
            Integer testCaseCount = testCaseCounts.getOrDefault(testSuiteDto.getId(), 0);
            testSuiteDto.setTestCaseCount(testCaseCount);
        });

        ResponseListDto<TestSuiteDto> responseListDto = new ResponseListDto(
                testSuiteDtoPage.getContent(),
                testSuiteDtoPage.getTotalElements(),
                testSuiteDtoPage.getTotalPages()
        );

        apiMessageDto.setMessage("List test suites success.");
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @PutMapping(value = "/update-flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TSU_UF')")
    public ApiMessageDto<String> updateFlagTestSuite(@RequestBody ModifyFlagForm modifyFlagForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        TestSuite testSuite = testSuiteRepository.findById(modifyFlagForm.getObjectId()).orElse(null);
        if (testSuite == null) {
            throw new BadRequestException("Test suite is not existed!", ErrorCode.TEST_SUITE_ERROR_NOT_EXIST);
        }
        testSuite.setFlag(modifyFlagForm.getFlag());
        testSuiteRepository.save(testSuite);
        apiMessageDto.setMessage("Update test suite flag success.");
        return apiMessageDto;
    }
}
