package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testexecutionturn.TestExecutionTurnDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testexecutionturn.CreateTestExecutionTurnForm;
import com.base.meta.form.testexecutionturn.UpdateTestExecutionTurnForm;
import com.base.meta.mapper.TestExecutionTurnMapper;
import com.base.meta.model.Account;
import com.base.meta.model.TestExecution;
import com.base.meta.model.TestExecutionTurn;
import com.base.meta.model.criteria.TestExecutionTurnCriteria;
import com.base.meta.repository.*;
import com.base.meta.service.BaseMetaApiService;
import io.swagger.annotations.Api;
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
@RequestMapping("/v1/test-execution-turn")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestExecutionTurnController extends ABasicController{
    @Autowired
    TestExecutionTurnRepository testExecutionTurnRepository;
    @Autowired
    TestExecutionTurnMapper testExecutionTurnMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TestExecutionRepository testExecutionRepository;
    @Autowired
    BaseMetaApiService baseMetaApiService;
    @Autowired
    TestStepExecutionRepository testStepExecutionRepository;
    @Autowired
    TestCaseExecutionRepository testCaseExecutionRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TET_C')")
    public ApiMessageDto<String> createTestExecutionTurn(@Valid @RequestBody CreateTestExecutionTurnForm createTestExecutionTurnForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("You are not authorized to create test execution turn");
        }

        Account assignedDeveloper = accountRepository.findById(createTestExecutionTurnForm.getAssignedDeveloperId()).orElse(null);
        if (assignedDeveloper == null){
            throw new BadRequestException("Assigned developer not found", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        if(assignedDeveloper.getKind() != BaseMetaConstant.USER_KIND_DEV){
            throw new BadRequestException("This account is not a developer", ErrorCode.ACCOUNT_ERROR_NOT_KIND_DEV);
        }

        TestExecution testExecution = testExecutionRepository.findById(createTestExecutionTurnForm.getTestExecutionId()).orElse(null);
        if (testExecution == null){
            throw new BadRequestException("Test execution not found", ErrorCode.TEST_EXECUTION_ERROR_NOT_EXIST);
        }

        TestExecutionTurn testExecutionTurn = testExecutionTurnRepository.findFirstByTurnNumber(createTestExecutionTurnForm.getTurnNumber());
        if (testExecutionTurn != null){
            throw new BadRequestException("Turn number already exists", ErrorCode.TEST_EXECUTION_TURN_ERROR_EXIST);
        }

        if(!baseMetaApiService.checkStartDateIsAfterNow(createTestExecutionTurnForm.getPlanStartDate())){
            throw new BadRequestException("Plan start date must be after now", ErrorCode.ERROR_DATE_INVALID);
        }

        if(!baseMetaApiService.checkStartDateIsBeforeEndDate(createTestExecutionTurnForm.getPlanEndDate(), createTestExecutionTurnForm.getPlanStartDate())){
            throw new BadRequestException("Plan end date must be after plan start date", ErrorCode.ERROR_DATE_INVALID);
        }

        if (!baseMetaApiService.checkEndDateIsAfterNow(createTestExecutionTurnForm.getPlanEndDate())){
            throw new BadRequestException("Plan end date must be after now", ErrorCode.ERROR_DATE_INVALID);
        }

        if(!baseMetaApiService.checkStartDateIsAfterNow(createTestExecutionTurnForm.getActualStartDate())){
            throw new BadRequestException("Actual start date must be after now", ErrorCode.ERROR_DATE_INVALID);
        }

        if(!baseMetaApiService.checkEndDateIsAfterNow(createTestExecutionTurnForm.getActualEndDate())){
            throw new BadRequestException("Actual end date must be after now", ErrorCode.ERROR_DATE_INVALID);
        }

        if (!baseMetaApiService.checkStartDateIsBeforeEndDate(createTestExecutionTurnForm.getActualEndDate(), createTestExecutionTurnForm.getActualStartDate())){
            throw new BadRequestException("Actual end date must be after actual start date", ErrorCode.ERROR_DATE_INVALID);
        }

        testExecutionTurn = testExecutionTurnMapper.fromCreateTestExecutionTurnFormToEntity(createTestExecutionTurnForm);
        testExecutionTurn.setAssignedDeveloper(assignedDeveloper);
        testExecutionTurn.setTestExecution(testExecution);
        testExecutionTurnRepository.save(testExecutionTurn);
        apiMessageDto.setMessage("Create test execution turn successfully");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TET_U')")
    public ApiMessageDto<String> updateTestExecutionTurn(@Valid @RequestBody UpdateTestExecutionTurnForm updateTestExecutionTurnForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("You are not authorized to update test execution turn");
        }
        TestExecutionTurn testExecutionTurn = testExecutionTurnRepository.findFirstById(updateTestExecutionTurnForm.getId());
        if(testExecutionTurn == null){
            throw new NotFoundException("Test execution turn not found", ErrorCode.TEST_EXECUTION_TURN_ERROR_NOT_EXIST);
        }

        if(!baseMetaApiService.checkStartDateIsAfterNow(updateTestExecutionTurnForm.getPlanStartDate())){
            throw new BadRequestException("Plan start date must be after now", ErrorCode.ERROR_DATE_INVALID);
        }

        if(!baseMetaApiService.checkStartDateIsBeforeEndDate(updateTestExecutionTurnForm.getPlanEndDate(), updateTestExecutionTurnForm.getPlanStartDate())){
            throw new BadRequestException("Plan end date must be after plan start date", ErrorCode.ERROR_DATE_INVALID);
        }

        if (!baseMetaApiService.checkEndDateIsAfterNow(updateTestExecutionTurnForm.getPlanEndDate())){
            throw new BadRequestException("Plan end date must be after now", ErrorCode.ERROR_DATE_INVALID);
        }

        if(!baseMetaApiService.checkStartDateIsAfterNow(updateTestExecutionTurnForm.getActualStartDate())){
            throw new BadRequestException("Actual start date must be after now", ErrorCode.ERROR_DATE_INVALID);
        }

        if(!baseMetaApiService.checkEndDateIsAfterNow(updateTestExecutionTurnForm.getActualEndDate())){
            throw new BadRequestException("Actual end date must be after now", ErrorCode.ERROR_DATE_INVALID);
        }

        if (!baseMetaApiService.checkStartDateIsBeforeEndDate(updateTestExecutionTurnForm.getActualEndDate(), updateTestExecutionTurnForm.getActualStartDate())){
            throw new BadRequestException("Actual end date must be after actual start date", ErrorCode.ERROR_DATE_INVALID);
        }
        Account assignedDeveloper = accountRepository.findById(updateTestExecutionTurnForm.getAssignedDeveloperId()).orElse(null);
        if(assignedDeveloper == null){
            throw new NotFoundException("Assigned developer not found", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        if(assignedDeveloper.getKind() != BaseMetaConstant.USER_KIND_DEV){
            throw new BadRequestException("This account is not a developer", ErrorCode.ACCOUNT_ERROR_NOT_KIND_DEV);
        }

        if(updateTestExecutionTurnForm.getTurnNumber() != null && !updateTestExecutionTurnForm.getTurnNumber().equals(testExecutionTurn.getTurnNumber())){
            TestExecutionTurn testExecutionTurnByTurnNumber = testExecutionTurnRepository.findFirstByTurnNumber(updateTestExecutionTurnForm.getTurnNumber());
            if(testExecutionTurnByTurnNumber != null){
                throw new BadRequestException("Turn number already exists", ErrorCode.TEST_EXECUTION_TURN_ERROR_EXIST);
            }
        }

        testExecutionTurnMapper.updateTestExecutionTurnFromToEntity(updateTestExecutionTurnForm, testExecutionTurn);
        testExecutionTurn.setAssignedDeveloper(assignedDeveloper);
        testExecutionTurnRepository.save(testExecutionTurn);
        apiMessageDto.setMessage("Update test execution turn successfully");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TET_D')")
    public ApiMessageDto<String> deleteTestExecutionTurn(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("You are not authorized to delete test execution turn");
        }
        TestExecutionTurn testExecutionTurn = testExecutionTurnRepository.findFirstById(id);
        if(testExecutionTurn == null){
            throw new BadRequestException("Test execution turn not found", ErrorCode.TEST_EXECUTION_TURN_ERROR_NOT_EXIST);
        }
        testExecutionTurnRepository.delete(testExecutionTurn);
        apiMessageDto.setMessage("Delete test execution turn successfully");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('TET_R')")
    public ApiMessageDto<TestExecutionTurnDto> getTestExecutionTurn(@PathVariable Long id){
        ApiMessageDto<TestExecutionTurnDto> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("You are not authorized to get test execution turn");
        }
        TestExecutionTurn testExecutionTurn = testExecutionTurnRepository.findFirstById(id);
        if(testExecutionTurn == null){
            throw new BadRequestException("Test execution turn not found", ErrorCode.TEST_EXECUTION_TURN_ERROR_NOT_EXIST);
        }

        TestExecutionTurnDto testExecutionTurnDto = testExecutionTurnMapper.fromEntityToDto(testExecutionTurn);

        Integer countByIsDefected = testStepExecutionRepository.countByIsDefected();
        testExecutionTurnDto.setTestDefectCount(countByIsDefected);

        Integer countAllTestCaseExecution = testCaseExecutionRepository.countByTestExecutionTurnId(testExecutionTurn.getId());
        testExecutionTurnDto.setTotalCasesCount(countAllTestCaseExecution);

        apiMessageDto.setData(testExecutionTurnMapper.fromEntityToDto(testExecutionTurn));
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestExecutionTurnDto>> listExecutionTurn(TestExecutionTurnCriteria testExecutionTurnCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<TestExecutionTurnDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestExecutionTurn> testExecutionTurnPage = testExecutionTurnRepository.findAll(testExecutionTurnCriteria.getSpecification(), pageable);
        ResponseListDto<TestExecutionTurnDto> responseListDto = new ResponseListDto(testExecutionTurnPage.getContent(), testExecutionTurnPage.getTotalElements(), testExecutionTurnPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List test execution turn success.");
        return apiMessageDto;
    }
}
