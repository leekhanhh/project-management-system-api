package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testcase.TestCaseDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testcase.CreateTestCaseForm;
import com.base.meta.form.testcase.UpdateTestCaseForm;
import com.base.meta.mapper.TestCaseMapper;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.TestCase;
import com.base.meta.model.criteria.TestCaseCriteria;
import com.base.meta.repository.ProgramRepository;
import com.base.meta.repository.ProjectRepository;
import com.base.meta.repository.TestCaseRepository;
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
@RequestMapping("/v1/test-case")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestCaseController extends ABasicController{
    @Autowired
    TestCaseRepository testCaseRepository;
    @Autowired
    TestCaseMapper testCaseMapper;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProgramRepository programRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TC_C')")
    public ApiMessageDto<String> createTestCase(@Valid @RequestBody CreateTestCaseForm createTestCaseForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allowed create!");
        }

        Program program = programRepository.findById(createTestCaseForm.getProgramId()).orElse(null);
        if (program == null) {
            throw new BadRequestException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
        }

        if (testCaseRepository.findFirstByName(createTestCaseForm.getName()) != null) {
            throw new BadRequestException("Test case name is existed!", ErrorCode.TEST_CASE_ERROR_NAME_EXISTED);
        }
        TestCase testCase = testCaseMapper.fromCreateTestCaseFormToEntity(createTestCaseForm);
        testCase.setProgram(program);
        testCaseRepository.save(testCase);
        apiMessageDto.setMessage("Create a new test case success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TC_U')")
    public ApiMessageDto<String> updateTestCase(@Valid @RequestBody UpdateTestCaseForm updateTestCaseForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allowed update!");
        }
        TestCase testCase = testCaseRepository.findById(updateTestCaseForm.getId()).orElse(null);
        if (testCase == null) {
            throw new BadRequestException("Test case is not existed!", ErrorCode.TEST_CASE_ERROR_NOT_EXIST);
        }
        if (testCaseRepository.findFirstByName(updateTestCaseForm.getName()) != null) {
            throw new BadRequestException("Test case name is existed!", ErrorCode.TEST_CASE_ERROR_NAME_EXISTED);
        }
        testCaseMapper.fromUpdateTestCaseFormToEntity(updateTestCaseForm, testCase);
        testCaseRepository.save(testCase);
        apiMessageDto.setMessage("Update test case success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TC_D')")
    public ApiMessageDto<String> deleteTestCase(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allowed delete!");
        }
        TestCase testCase = testCaseRepository.findById(id).orElse(null);
        if (testCase == null) {
            throw new BadRequestException("Test case is not existed!", ErrorCode.TEST_CASE_ERROR_NOT_EXIST);
        }
        testCaseRepository.delete(testCase);
        apiMessageDto.setMessage("Delete test case success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestCaseDto>> listTestCase(TestCaseCriteria testCaseCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestCaseDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestCase> testCasePage = testCaseRepository.findAll(testCaseCriteria.getSpecification(), pageable);
        ResponseListDto<TestCaseDto> responseListDto = new ResponseListDto(testCaseMapper.fromEntityToTestCaseDtoList(testCasePage.getContent()), testCasePage.getTotalElements(), testCasePage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List test cases success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestCaseDto> getTestCase(@PathVariable("id") Long id) {
        ApiMessageDto<TestCaseDto> apiMessageDto = new ApiMessageDto<>();
        TestCase testCase = testCaseRepository.findById(id).orElse(null);
        if (testCase == null) {
            throw new BadRequestException("Test case is not existed!", ErrorCode.TEST_CASE_ERROR_NOT_EXIST);
        }
        apiMessageDto.setData(testCaseMapper.fromEntityToTestCaseDto(testCase));
        apiMessageDto.setMessage("Get test case success.");
        return apiMessageDto;
    }

}
