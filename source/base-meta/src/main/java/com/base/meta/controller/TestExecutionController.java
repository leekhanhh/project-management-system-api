package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testexecution.TestExecutionDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testexecution.CreateTestExecutionForm;
import com.base.meta.form.testexecution.UpdateTestExecutionForm;
import com.base.meta.mapper.TestExecutionMapper;
import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.Program;
import com.base.meta.model.TestExecution;
import com.base.meta.model.criteria.TestExecutionCriteria;
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
import java.util.Objects;

@RestController
@RequestMapping("/v1/test-execution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestExecutionController extends ABasicController {
    private static final String PREFIX_ENTITY = "TE";
    @Autowired
    TestExecutionRepository testExecutionRepository;
    @Autowired
    TestExecutionMapper testExecutionMapper;
    @Autowired
    ProgramRepository programRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TestExecutionTurnRepository testExecutionTurnRepository;
    @Autowired
    BaseMetaApiService baseMetaApiService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TE_C')")
    public ApiMessageDto<String> createTestExecution(@Valid @RequestBody CreateTestExecutionForm createTestExecutionForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), ErrorCode.TEST_EXECUTION_ERROR_INVALID);
        }
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed create!");
        }
        Account account = accountRepository.findById(createTestExecutionForm.getAssignedDeveloperId()).orElseThrow(()
                -> new NotFoundException("Account is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND));
        TestExecution testExecution = testExecutionRepository.findByName(createTestExecutionForm.getName());
        if (testExecution != null) {
            throw new BadRequestException("TestExecution name is existed!", ErrorCode.TEST_EXECUTION_ERROR_EXISTED);
        }
        Category category = categoryRepository.findFirstById(createTestExecutionForm.getCategoryId());
        if (category == null) {
            throw new NotFoundException("Category is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category status = categoryRepository.findFirstById(createTestExecutionForm.getStatusId());
        if (status == null) {
            throw new NotFoundException("Status is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Program program = programRepository.findById(createTestExecutionForm.getProgramId()).orElseThrow(()
                -> new NotFoundException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST));

        testExecution = testExecutionMapper.fromCreateTestExecutionFormToEntity(createTestExecutionForm);
        testExecution.setAssignedDeveloper(account);
        testExecution.setCategory(category);
        testExecution.setStatus(status);
        testExecution.setProgram(program);
        testExecution.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        testExecutionRepository.save(testExecution);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Create test execution successfully!");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TE_U')")
    public ApiMessageDto<String> updateTestExecution(@Valid UpdateTestExecutionForm updateTestExecutionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed update!");
        }
        Account assignedDeveloper = accountRepository.findById(updateTestExecutionForm.getAssignedDeveloperId()).orElse(null);
        if (assignedDeveloper == null) {
            throw new NotFoundException("Account is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        TestExecution testExecution = testExecutionRepository.findFirstById(updateTestExecutionForm.getId());
        if (testExecution == null) {
            throw new NotFoundException("TestExecution is not existed!", ErrorCode.TEST_EXECUTION_ERROR_EXISTED);
        }
        Category category = categoryRepository.findFirstById(updateTestExecutionForm.getCategoryId());
        if (category == null) {
            throw new NotFoundException("Category is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category status = categoryRepository.findFirstById(updateTestExecutionForm.getStatusId());
        if (status == null) {
            throw new NotFoundException("Status is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        if(!testExecution.getName().equals(updateTestExecutionForm.getName())){
            TestExecution testExecutionCheck = testExecutionRepository.findByName(updateTestExecutionForm.getName());
            if (testExecutionCheck != null) {
                throw new BadRequestException("TestExecution name is existed!", ErrorCode.TEST_EXECUTION_ERROR_EXISTED);
            }
        }

        testExecutionMapper.updateTestExecutionFromToEntity(updateTestExecutionForm, testExecution);
        testExecution.setAssignedDeveloper(assignedDeveloper);
        testExecution.setCategory(category);
        testExecution.setStatus(status);
        testExecutionRepository.save(testExecution);
        apiMessageDto.setMessage("Update test execution successfully!");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TE_D')")
    public ApiMessageDto<String> deleteTestExecution(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        TestExecution testExecution = testExecutionRepository.findFirstById(id);
        if (testExecution == null) {
            throw new BadRequestException("TestExecution is not existed!", ErrorCode.TEST_EXECUTION_ERROR_EXISTED);
        }
        testExecutionRepository.delete(testExecution);
        apiMessageDto.setMessage("Delete test execution successfully!");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestExecutionDto> getTestExecution(@PathVariable Long id) {
        ApiMessageDto<TestExecutionDto> apiMessageDto = new ApiMessageDto<>();
        TestExecution testExecution = testExecutionRepository.findFirstById(id);
        if (testExecution == null) {
            throw new BadRequestException("TestExecution is not existed!", ErrorCode.TEST_EXECUTION_ERROR_EXISTED);
        }
        Integer testExecutionTurnCount = testExecutionTurnRepository.countTestExecutionTurnByTestExecution(testExecution.getId());
        TestExecutionDto testExecutionDto = testExecutionMapper.fromEntityToTestExecutionDto(testExecution);
        testExecutionDto.setTestExecutionTurnCount(testExecutionTurnCount);
        apiMessageDto.setData(testExecutionDto);
        apiMessageDto.setMessage("Get test execution successfully!");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestExecutionDto>> listTestExecution(TestExecutionCriteria testExecutionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestExecutionDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestExecutionDto> testExecutionPage = testExecutionRepository.findAll(testExecutionCriteria.getSpecification(), pageable).map(testExecutionMapper::fromEntityToTestExecutionDto);
        ResponseListDto<TestExecutionDto> responseListDto = new ResponseListDto(testExecutionPage.getContent(), testExecutionPage.getTotalElements(), testExecutionPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list test execution successfully!");
        return apiMessageDto;
    }

}
