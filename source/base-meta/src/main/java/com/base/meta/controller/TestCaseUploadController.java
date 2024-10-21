package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testcaseupload.TestCaseUploadDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.testcaseupload.CreateTestCaseUploadForm;
import com.base.meta.form.testcaseupload.UpdateTestCaseUploadForm;
import com.base.meta.mapper.TestCaseUploadMapper;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.TestCaseUpload;
import com.base.meta.model.criteria.TestCaseUploadCriteria;
import com.base.meta.repository.ProgramRepository;
import com.base.meta.repository.ProjectRepository;
import com.base.meta.repository.TestCaseUploadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/test-case-upload")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestCaseUploadController extends ABasicController{
    @Autowired
    TestCaseUploadRepository testCaseUploadRepository;
    @Autowired
    TestCaseUploadMapper testCaseUploadMapper;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProgramRepository programRepository;

    @PostMapping(value="/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TCU_C')")
    public ApiMessageDto<TestCaseUploadDto> createTestCaseUpload(@Valid CreateTestCaseUploadForm createTestCaseUploadForm, BindingResult bindingResult) {
        ApiMessageDto<TestCaseUploadDto> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allowed create!");
        }
        Program program = programRepository.findById(createTestCaseUploadForm.getProgramId()).orElse(null);
        if (program == null) {
            throw new BadRequestException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
        }
        TestCaseUpload testCaseUpload = testCaseUploadMapper.fromCreateTestCaseUploadFormToEntity(createTestCaseUploadForm);
        testCaseUpload.setProgram(program);
        testCaseUploadRepository.save(testCaseUpload);
        apiMessageDto.setData(testCaseUploadMapper.fromEntityToTestCaseUploadDto(testCaseUpload));
        apiMessageDto.setMessage("Create test case upload success.");
        return apiMessageDto;
    }

//    @PostMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Transactional
//    @PreAuthorize("hasRole('TCU_U')")
//    public ApiMessageDto<TestCaseUploadDto> updateTestCaseUpload(@Valid UpdateTestCaseUploadForm updateTestCaseUploadForm, BindingResult bindingResult) {
//        ApiMessageDto<TestCaseUploadDto> apiMessageDto = new ApiMessageDto<>();
//        if(!isTester()){
//            throw new UnauthorizationException("Not allowed update!");
//        }
//        Program program = programRepository.findById(updateTestCaseUploadForm.getProgramId()).orElse(null);
//        if (program == null) {
//            throw new BadRequestException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
//        }
//        TestCaseUpload testCaseUpload = testCaseUploadRepository.findById(updateTestCaseUploadForm.getId()).orElse(null);
//        if (testCaseUpload == null) {
//            throw new BadRequestException("Test case upload is not existed!", ErrorCode.TEST_CASE_UPLOAD_ERROR_NOT_EXIST);
//        }
//        testCaseUploadMapper.(updateTestCaseUploadForm, testCaseUpload);
//        testCaseUpload.setProgram(program);
//        testCaseUploadRepository.save(testCaseUpload);
//        apiMessageDto.setData(testCaseUploadMapper.fromEntityToTestCaseUploadDto(testCaseUpload));
//        apiMessageDto.setMessage("Update test case upload success.");
//        return apiMessageDto;
//    }

    @PostMapping(value="/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TCU_D')")
    public ApiMessageDto<String> deleteTestCaseUpload(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isTester()){
            throw new UnauthorizationException("Not allowed delete!");
        }
        TestCaseUpload testCaseUpload = testCaseUploadRepository.findById(id).orElse(null);
        if (testCaseUpload == null) {
            throw new BadRequestException("Test case upload is not existed!", ErrorCode.TEST_CASE_UPLOAD_ERROR_NOT_EXIST);
        }
        testCaseUploadRepository.delete(testCaseUpload);
        apiMessageDto.setMessage("Delete test case upload success.");
        return apiMessageDto;
    }

    @PostMapping(value="/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestCaseUploadDto> getTestCaseUpload(Long id) {
        ApiMessageDto<TestCaseUploadDto> apiMessageDto = new ApiMessageDto<>();
        TestCaseUpload testCaseUpload = testCaseUploadRepository.findById(id).orElse(null);
        if (testCaseUpload == null) {
            throw new BadRequestException("Test case upload is not existed!", ErrorCode.TEST_CASE_UPLOAD_ERROR_NOT_EXIST);
        }
        apiMessageDto.setData(testCaseUploadMapper.fromEntityToTestCaseUploadDto(testCaseUpload));
        apiMessageDto.setMessage("Get test case upload success.");
        return apiMessageDto;
    }

    @PostMapping(value="/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestCaseUploadDto>> listTestCaseUpload(TestCaseUploadCriteria testCaseUploadCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestCaseUploadDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestCaseUpload> testCaseUploadPage = testCaseUploadRepository.findAll(testCaseUploadCriteria.getSpecification(), pageable);
        ResponseListDto<TestCaseUploadDto> responseListDto = new ResponseListDto(testCaseUploadMapper.fromEntityToTestCaseUploadDtoList(testCaseUploadPage.getContent()), testCaseUploadPage.getTotalElements(), testCaseUploadPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list test case upload success.");
        return apiMessageDto;
    }

}
