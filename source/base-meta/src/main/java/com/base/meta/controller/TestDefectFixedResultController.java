package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testdefect.TestDefectDto;
import com.base.meta.dto.testdefectfixedresult.TestDefectFixedResultDto;
import com.base.meta.exception.NotFoundException;
import com.base.meta.form.test.defect.fixed.result.CreateTestDefectFixedResultForm;
import com.base.meta.form.test.defect.fixed.result.UpdateTestDefectFixedResultForm;
import com.base.meta.form.testdefect.UpdateTestDefectForm;
import com.base.meta.mapper.TestDefectFixedResultMapper;
import com.base.meta.model.*;
import com.base.meta.model.criteria.TestDefectCriteria;
import com.base.meta.model.criteria.TestDefectFixedResultCriteria;
import com.base.meta.repository.TestDefectFixedResultRepository;
import com.base.meta.repository.TestDefectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/test-defect-fixed-result")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestDefectFixedResultController extends ABasicController {
    @Autowired
    TestDefectFixedResultRepository testDefectFixedResultRepository;
    @Autowired
    TestDefectRepository testDefectRepository;
    @Autowired
    TestDefectFixedResultMapper testDefectFixedResultMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> createTestDefectFixedResult(@Valid @RequestBody CreateTestDefectFixedResultForm createTestDefectFixedResultForm, BindingResult bindingResult) {
        TestDefect testDefect = testDefectRepository.findFirstById(createTestDefectFixedResultForm.getTestDefectId()).orElseThrow(
                () -> new NotFoundException("Test Defect not found!", ErrorCode.TEST_DEFECT_ERROR_NOT_EXIST)
        );
        TestDefectFixedResult testDefectFixedResult = testDefectFixedResultMapper.fromCreateTestDefectFixedResultFormToEntity(createTestDefectFixedResultForm);
        testDefectFixedResult.setTestDefect(testDefect);
        testDefectFixedResultRepository.save(testDefectFixedResult);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Create Test Defect Fixed Result successfully.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateTestDefectFixedResult(@Valid @RequestBody UpdateTestDefectFixedResultForm updateTestDefectFixedResultForm, BindingResult bindingResult) {
        TestDefectFixedResult testDefectFixedResult = testDefectFixedResultRepository.findFirstById(updateTestDefectFixedResultForm.getId()).orElseThrow(
                () -> new NotFoundException("Test Defect Fixed Result not found!", ErrorCode.TEST_DEFECT_FIXED_RESULT_ERROR_NOT_EXIST)
        );
        testDefectFixedResultMapper.mappingUpdateTestDefectFixedResultFormToEntity(updateTestDefectFixedResultForm, testDefectFixedResult);
        testDefectFixedResultRepository.save(testDefectFixedResult);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Update a Test Defect Fixed Result successfully.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestDefectFixedResultDto> getTestDefectFixedResult(@PathVariable Long id) {
        TestDefectFixedResult testDefectFixedResult = testDefectFixedResultRepository.findFirstById(id).orElseThrow(
                () -> new NotFoundException("Test Defect Fixed Result not found!", ErrorCode.TEST_DEFECT_FIXED_RESULT_ERROR_NOT_EXIST)
        );
        ApiMessageDto<TestDefectFixedResultDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(testDefectFixedResultMapper.fromEntityToDto(testDefectFixedResult));
        apiMessageDto.setMessage("Get a Test Defect Fixed Result successfully.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestDefectFixedResultDto>> listTestDefectFixedResult(TestDefectFixedResultCriteria criteria, Pageable pageable) {
        Page<TestDefectFixedResult> page = testDefectFixedResultRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto<TestDefectFixedResultDto> responseListDto = new ResponseListDto(testDefectFixedResultMapper.fromEntityListToDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        ApiMessageDto<ResponseListDto<TestDefectFixedResultDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List Test Defect Fixed Result successfully.");
        return apiMessageDto;
    }
}
