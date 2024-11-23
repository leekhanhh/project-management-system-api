package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testplan.TestPlanDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.ModifyFlagForm;
import com.base.meta.form.testplan.CreateTestPlanForm;
import com.base.meta.form.testplan.UpdateTestPlanForm;
import com.base.meta.mapper.TestPlanMapper;
import com.base.meta.model.Program;
import com.base.meta.model.TestPlan;
import com.base.meta.model.criteria.TestPlanCriteria;
import com.base.meta.repository.ProgramRepository;
import com.base.meta.repository.TestPlanRepository;
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
@RequestMapping("/v1/test-plan")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestPlanController extends ABasicController{
    private static final String PREFIX_ENTITY = "TP";
    @Autowired
    TestPlanRepository testPlanRepository;
    @Autowired
    TestPlanMapper testPlanMapper;
    @Autowired
    ProgramRepository programRepository;
    @Autowired
    BaseMetaApiService baseMetaApiService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TP_C')")
    public ApiMessageDto<String> createTestPlan(@Valid @RequestBody CreateTestPlanForm createTestPlanForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isPM()){
            throw new UnauthorizationException("Not allow create test plan");
        }

        Program program = programRepository.findById(createTestPlanForm.getProgramId()).orElseThrow(()
                -> new NotFoundException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST));

        TestPlan testPlan = testPlanRepository.findFirstByNameAndProgramId(createTestPlanForm.getName(), program.getId());
        if (testPlan != null) {
            throw new BadRequestException("Test plan name is existed!", ErrorCode.TEST_PLAN_ERROR_NAME_EXISTED);
        }

        if(createTestPlanForm.getStartDate().after(createTestPlanForm.getEndDate())){
            throw new BadRequestException("Start date must be before end date!", ErrorCode.TEST_PLAN_ERROR_DATE_INVALID);
        }
        testPlan = testPlanMapper.fromCreateTestPlanFormToEntity(createTestPlanForm);
        testPlan.setProgram(program);
        testPlan.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        testPlanRepository.save(testPlan);
        apiMessageDto.setMessage("Create test plan success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TP_U')")
    public ApiMessageDto<String> updateTestPlan(@Valid @RequestBody UpdateTestPlanForm updateTestPlanForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isPM()){
            throw new UnauthorizationException("Not allow update test plan");
        }

        TestPlan testPlan = testPlanRepository.findById(updateTestPlanForm.getId()).orElseThrow(()
                -> new NotFoundException("Test plan is not existed!", ErrorCode.TEST_PLAN_ERROR_NOT_EXIST));

        if (testPlan.getName() != updateTestPlanForm.getName() && testPlanRepository.findFirstByNameAndProgramId(updateTestPlanForm.getName(), testPlan.getProgram().getId()) != null) {
            throw new BadRequestException("Test plan name is existed!", ErrorCode.TEST_PLAN_ERROR_NAME_EXISTED);
        }

        if(updateTestPlanForm.getStartDate().after(updateTestPlanForm.getEndDate())){
            throw new BadRequestException("Start date must be before end date!", ErrorCode.TEST_PLAN_ERROR_DATE_INVALID);
        }

        testPlanMapper.updateTestPlanFromEntity(updateTestPlanForm, testPlan);
        testPlanRepository.save(testPlan);
        apiMessageDto.setMessage("Update test plan success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TP_D')")
    public ApiMessageDto<String> deleteTestPlan(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isPM()){
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

    @PutMapping(value = "/update-flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TP_UF')")
    public ApiMessageDto<String> updateFlagTestPlan(@RequestBody ModifyFlagForm modifyFlagForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isPM()){
            throw new UnauthorizationException("Not allow delete test plan");
        }
        TestPlan testPlan = testPlanRepository.findById(modifyFlagForm.getObjectId()).orElseThrow(()
                -> new NotFoundException("Test plan is not existed!", ErrorCode.TEST_PLAN_ERROR_NOT_EXIST));
        testPlan.setFlag(modifyFlagForm.getFlag());
        testPlanRepository.save(testPlan);
        apiMessageDto.setMessage("Update test plan flag success.");
        return apiMessageDto;
    }
}
