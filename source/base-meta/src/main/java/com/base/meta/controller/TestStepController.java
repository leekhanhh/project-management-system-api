package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.teststep.TestStepDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.teststep.CreateTestStepForm;
import com.base.meta.form.teststep.UpdateTestStepForm;
import com.base.meta.mapper.TestStepMapper;
import com.base.meta.model.TestCase;
import com.base.meta.model.TestStep;
import com.base.meta.model.criteria.TestStepCriteria;
import com.base.meta.repository.TestCaseRepository;
import com.base.meta.repository.TestStepRepository;
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
@RequestMapping("/v1/test-step")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestStepController extends ABasicController{
    @Autowired
    TestStepRepository testStepRepository;
    @Autowired
    TestStepMapper testStepMapper;
    @Autowired
    TestCaseRepository testCaseRepository;

    @PostMapping(value="/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TST_C')")
    public ApiMessageDto<TestStepDto> createTestStep(@Valid CreateTestStepForm createTestStepForm, BindingResult bindingResult) {
        ApiMessageDto<TestStepDto> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allowed create!");
        }
        TestCase testCase = testCaseRepository.findById(createTestStepForm.getTestCaseId()).orElse(null);
        if (testCase == null) {
            throw new BadRequestException("Test case is not existed!", ErrorCode.TEST_CASE_ERROR_NOT_EXIST);
        }
        TestStep testStep = testStepMapper.fromCreateTestStepFormToEntity(createTestStepForm);
        testStep.setTestCase(testCase);
        testStepRepository.save(testStep);
        apiMessageDto.setData(testStepMapper.fromEntityToTestStepDto(testStep));
        apiMessageDto.setMessage("Create test step success.");
        return apiMessageDto;
    }

    @PutMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TST_U')")
    public ApiMessageDto<TestStepDto> updateTestStep(@Valid UpdateTestStepForm updateTestStepForm, BindingResult bindingResult) {
        ApiMessageDto<TestStepDto> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allowed update!");
        }
        TestStep testStep = testStepRepository.findById(updateTestStepForm.getId()).orElse(null);
        if (testStep == null) {
            throw new BadRequestException("Test step is not existed!", ErrorCode.TEST_STEP_ERROR_NOT_EXIST);
        }
        testStepMapper.updateTestStepFromEntity(updateTestStepForm, testStep);
        testStepRepository.save(testStep);
        apiMessageDto.setData(testStepMapper.fromEntityToTestStepDto(testStep));
        apiMessageDto.setMessage("Update test step success.");
        return apiMessageDto;
    }

    @DeleteMapping(value="/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TST_D')")
    public ApiMessageDto<Long> deleteTestStep(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allowed delete!");
        }
        TestStep testStep = testStepRepository.findById(id).orElse(null);
        if (testStep == null) {
            throw new BadRequestException("Test step is not existed!", ErrorCode.TEST_STEP_ERROR_NOT_EXIST);
        }
        testStepRepository.delete(testStep);
        apiMessageDto.setData(id);
        apiMessageDto.setMessage("Delete test step success.");
        return apiMessageDto;
    }

    @GetMapping(value="/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestStepDto> getTestStep(@PathVariable("id") Long id) {
        ApiMessageDto<TestStepDto> apiMessageDto = new ApiMessageDto<>();
        TestStep testStep = testStepRepository.findById(id).orElse(null);
        if (testStep == null) {
            throw new BadRequestException("Test step is not existed!", ErrorCode.TEST_STEP_ERROR_NOT_EXIST);
        }
        apiMessageDto.setData(testStepMapper.fromEntityToTestStepDto(testStep));
        apiMessageDto.setMessage("Get test step success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestStepDto>> listTestStep(TestStepCriteria testStepCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<TestStepDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestStep> testStepPage = testStepRepository.findAll(testStepCriteria.getSpecification(), pageable);
        ResponseListDto<TestStepDto> responseListDto = new ResponseListDto(testStepMapper.fromEntityToTestStepDtoList(testStepPage.getContent()), testStepPage.getTotalElements(), testStepPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List test step success.");
        return apiMessageDto;
    }



}
