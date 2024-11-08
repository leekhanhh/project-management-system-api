package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testdefect.TestDefectDto;
import com.base.meta.exception.NotFoundException;
import com.base.meta.form.testdefect.CreateTestDefectForm;
import com.base.meta.form.testdefect.UpdateTestDefectForm;
import com.base.meta.mapper.TestDefectMapper;
import com.base.meta.model.*;
import com.base.meta.model.criteria.TestDefectCriteria;
import com.base.meta.repository.AccountRepository;
import com.base.meta.repository.CategoryRepository;
import com.base.meta.repository.TestDefectRepository;
import com.base.meta.repository.TestStepExecutionRepository;
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
@RequestMapping("/v1/test-defect")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestDefectController extends ABasicController {
    @Autowired
    TestDefectRepository testDefectRepository;
    @Autowired
    TestDefectMapper testDefectMapper;
    @Autowired
    TestStepExecutionRepository testStepExecutionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TDE_C')")
    public ApiMessageDto<String> createTestDefect(@Valid @RequestBody CreateTestDefectForm createTestDefectForm, BindingResult bindingResult) {
        TestStepExecution testStepExecution = testStepExecutionRepository.findFirstById(createTestDefectForm.getTestStepExecutionId()).orElseThrow(
                () -> new NotFoundException("Test Step Execution not found!", ErrorCode.TEST_STEP_EXECUTION_ERROR_NOT_EXIST)
        );
        Account account = accountRepository.findFirstById(createTestDefectForm.getAssignedDeveloperId());
        if (account == null) {
            throw new NotFoundException("Assigned developer not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        Category category = categoryRepository.findFirstById(createTestDefectForm.getStatusId());
        if (category == null){
            throw new NotFoundException("Category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        TestDefect testDefect = testDefectMapper.fromCreateTestDefectFormToEntity(createTestDefectForm);
        testDefect.setTestStepExecution(testStepExecution);
        testDefect.setAssignedDeveloper(account);
        testDefect.setStatus(category);
        testDefectRepository.save(testDefect);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Create a new Test Defect successfully.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TDE_U')")
    public ApiMessageDto<String> updateTestDefect(@Valid @RequestBody UpdateTestDefectForm updateTestDefectForm, BindingResult bindingResult) {
        TestDefect testDefect = testDefectRepository.findFirstById(updateTestDefectForm.getId()).orElseThrow(
                () -> new NotFoundException("Test Defect not found!", ErrorCode.TEST_DEFECT_ERROR_NOT_EXIST)
        );
        TestStepExecution testStepExecution = testStepExecutionRepository.findFirstById(updateTestDefectForm.getId()).orElseThrow(
                () -> new NotFoundException("Test Step Execution not found!", ErrorCode.TEST_STEP_EXECUTION_ERROR_NOT_EXIST)
        );
        Category category = categoryRepository.findFirstById(updateTestDefectForm.getStatusId());
        if (category == null){
            throw new NotFoundException("Category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Account account = accountRepository.findFirstById(updateTestDefectForm.getAssignedDeveloperId());
        if (account == null) {
            throw new NotFoundException("Assigned developer not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }

        testDefectMapper.mappingUpdateTestDefectFormToEntity(updateTestDefectForm, testDefect);
        testDefect.setStatus(category);
        testDefect.setAssignedDeveloper(account);
        testDefect.setTestStepExecution(testStepExecution);
        testDefectRepository.save(testDefect);

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Update a Test Defect successfully.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestDefectDto> getTestDefect(@PathVariable Long id) {
        TestDefect testDefect = testDefectRepository.findFirstById(id).orElseThrow(
                () -> new NotFoundException("Test Defect not found!", ErrorCode.TEST_DEFECT_ERROR_NOT_EXIST)
        );
        ApiMessageDto<TestDefectDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(testDefectMapper.fromEntityToDto(testDefect));
        apiMessageDto.setMessage("Get a Test Defect successfully.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestDefectDto>> listTestDefect(TestDefectCriteria criteria, Pageable pageable) {
        Page<TestDefect> page = testDefectRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto<TestDefectDto> responseListDto = new ResponseListDto(testDefectMapper.fromEntityListToDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        ApiMessageDto<ResponseListDto<TestDefectDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List Test Defect successfully.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TDE_D')")
    public ApiMessageDto<String> deleteTestDefect(@PathVariable Long id) {
        TestDefect testDefect = testDefectRepository.findFirstById(id).orElseThrow(
                () -> new NotFoundException("Test Defect not found!", ErrorCode.TEST_DEFECT_ERROR_NOT_EXIST)
        );
        testDefectRepository.delete(testDefect);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Delete a Test Defect successfully.");
        return apiMessageDto;
    }
}
