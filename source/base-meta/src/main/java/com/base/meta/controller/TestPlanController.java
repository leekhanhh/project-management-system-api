package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testplan.TestPlanDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testplan.CreateTestPlanForm;
import com.base.meta.form.testplan.UpdateTestPlanForm;
import com.base.meta.mapper.TestPlanMapper;
import com.base.meta.model.Program;
import com.base.meta.model.TestPlan;
import com.base.meta.model.criteria.TestPlanCriteria;
import com.base.meta.repository.ProgramRepository;
import com.base.meta.repository.TestPlanRepository;
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
@RequestMapping("/v1/test-plan")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestPlanController extends ABasicController{
    @Autowired
    TestPlanRepository testPlanRepository;
    @Autowired
    TestPlanMapper testPlanMapper;
    @Autowired
    ProgramRepository programRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TP_C')")
    public ApiMessageDto<String> createTestPlan(@Valid @RequestBody CreateTestPlanForm createTestPlanForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allow create test plan");
        }

        Program program = programRepository.findById(createTestPlanForm.getProgramId()).orElse(null);
        if (program == null) {
            throw new BadRequestException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
        }

        TestPlan testPlan = testPlanRepository.findFirstByName(createTestPlanForm.getName());
        if (testPlan != null) {
            throw new BadRequestException("Test plan name is existed!", ErrorCode.TEST_PLAN_ERROR_NAME_EXISTED);
        }

        if(createTestPlanForm.getStartDate().before(new Date()) || createTestPlanForm.getEndDate().before(new Date())){
            throw new BadRequestException("Start date and end date must not before present!", ErrorCode.TEST_PLAN_ERROR_DATE_INVALID);
        }

        if(createTestPlanForm.getStartDate().after(createTestPlanForm.getEndDate())){
            throw new BadRequestException("Start date must be before end date!", ErrorCode.TEST_PLAN_ERROR_DATE_INVALID);
        }
        testPlan = testPlanMapper.fromCreateTestPlanFormToEntity(createTestPlanForm);
        testPlan.setProgram(program);
        testPlanRepository.save(testPlan);
        apiMessageDto.setMessage("Create test plan success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TP_U')")
    public ApiMessageDto<String> updateTestPlan(@Valid @RequestBody UpdateTestPlanForm updateTestPlanForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allow update test plan");
        }

        TestPlan testPlan = testPlanRepository.findFirstByName(updateTestPlanForm.getName());
        if (testPlan == null) {
            throw new BadRequestException("Test plan is not existed!", ErrorCode.TEST_PLAN_ERROR_NOT_EXIST);
        }

        if(updateTestPlanForm.getStartDate().before(new Date()) || updateTestPlanForm.getEndDate().before(new Date())){
            throw new BadRequestException("Start date and end date must not before present!", ErrorCode.TEST_PLAN_ERROR_DATE_INVALID);
        }

        if(updateTestPlanForm.getStartDate().after(updateTestPlanForm.getEndDate())){
            throw new BadRequestException("Start date must be before end date!", ErrorCode.TEST_PLAN_ERROR_DATE_INVALID);
        }

        testPlanMapper.updateTestPlanFromEntity(updateTestPlanForm, testPlan);
        apiMessageDto.setMessage("Update test plan success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TP_D')")
    public ApiMessageDto<String> deleteTestPlan(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allow delete test plan");
        }
        TestPlan testPlan = testPlanRepository.findById(id).orElse(null);
        if (testPlan == null) {
            throw new BadRequestException("Test plan is not existed!", ErrorCode.TEST_PLAN_ERROR_NOT_EXIST);
        }
        testPlanRepository.delete(testPlan);
        apiMessageDto.setMessage("Delete test plan success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestPlanDto> getTestPlan(@PathVariable Long id){
        ApiMessageDto<TestPlanDto> apiMessageDto = new ApiMessageDto<>();
        TestPlan testPlan = testPlanRepository.findById(id).orElse(null);
        if (testPlan == null) {
            throw new BadRequestException("Test plan is not existed!", ErrorCode.TEST_PLAN_ERROR_NOT_EXIST);
        }
        apiMessageDto.setData(testPlanMapper.fromEntityToTestPlanDto(testPlan));
        apiMessageDto.setMessage("Get test plan success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestPlanDto>> listTestPlan(TestPlanCriteria testPlanCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<TestPlanDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestPlan> testPlanPage = testPlanRepository.findAll(testPlanCriteria.getSpecification(), pageable);
        ResponseListDto<TestPlanDto> responseListDto = new ResponseListDto(testPlanMapper.fromEntityToTestPlanDtoList(testPlanPage.getContent()), testPlanPage.getTotalElements(), testPlanPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List test plans success.");
        return apiMessageDto;
    }
    
}
