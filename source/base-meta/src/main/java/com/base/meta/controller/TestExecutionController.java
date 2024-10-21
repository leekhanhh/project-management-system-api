package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testexecution.TestExecutionDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testexecution.CreateTestExecutionForm;
import com.base.meta.form.testexecution.UpdateTestExecutionForm;
import com.base.meta.mapper.TestExecutionMapper;
import com.base.meta.model.Account;
import com.base.meta.model.Category;
import com.base.meta.model.TestExecution;
import com.base.meta.model.criteria.TestExecutionCriteria;
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
@RequestMapping("/v1/test-execution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestExecutionController extends ABasicController {
    @Autowired
    TestExecutionRepository testExecutionRepository;
    @Autowired
    TestExecutionMapper testExecutionMapper;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TestExecutionTurnRepository testExecutionTurnRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TE_C')")
    public ApiMessageDto<String> createTestExecution(@Valid CreateTestExecutionForm createTestExecutionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed create!");
        }
        Account account = accountRepository.findById(createTestExecutionForm.getAssignedDeveloperId()).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        TestExecution testExecution = testExecutionRepository.findByName(createTestExecutionForm.getName());
        if (testExecution != null) {
            throw new BadRequestException("TestExecution name is existed!", ErrorCode.TEST_EXECUTION_ERROR_EXISTED);
        }
        Category category = categoryRepository.findFirstById(createTestExecutionForm.getCategoryId());
        if (category == null) {
            throw new BadRequestException("Category is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category status = categoryRepository.findFirstById(createTestExecutionForm.getStatusId());
        if (status == null) {
            throw new BadRequestException("Status is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        testExecution = testExecutionMapper.fromCreateTestExecutionFormToEntity(createTestExecutionForm);
        testExecution.setAssignedDeveloper(account);
        testExecutionRepository.save(testExecution);
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
        Account account = accountRepository.findById(updateTestExecutionForm.getAssignedDeveloperId()).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        TestExecution testExecution = testExecutionRepository.findFirstById(updateTestExecutionForm.getId());
        if (testExecution == null) {
            throw new BadRequestException("TestExecution is not existed!", ErrorCode.TEST_EXECUTION_ERROR_EXISTED);
        }
        Category category = categoryRepository.findFirstById(updateTestExecutionForm.getCategoryId());
        if (category == null) {
            throw new BadRequestException("Category is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category status = categoryRepository.findFirstById(updateTestExecutionForm.getStatusId());
        if (status == null) {
            throw new BadRequestException("Status is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        testExecutionMapper.updateTestExecutionFromToEntity(updateTestExecutionForm, testExecution);
        testExecution.setAssignedDeveloper(account);
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

        List<Long> testSuiteIds = testExecutionPage.getContent()
                .stream().map(TestExecutionDto::getId)
                .collect(Collectors.toList());
        Map<Long, Integer> testExecutionTurnCountMap = testExecutionTurnRepository.countTestExecutionTurnByTestExecutionIds(testSuiteIds);
        testExecutionPage.getContent().forEach(testExecutionDto -> {
            Integer testExecutionTurnCount = testExecutionTurnCountMap.getOrDefault(testExecutionDto.getId(), 0);
            testExecutionDto.setTestExecutionTurnCount(testExecutionTurnCount);
        });

        ResponseListDto<TestExecutionDto> responseListDto = new ResponseListDto(testExecutionPage.getContent(), testExecutionPage.getTotalElements(), testExecutionPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list test execution successfully!");
        return apiMessageDto;
    }

}
