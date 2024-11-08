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
import com.base.meta.model.*;
import com.base.meta.model.criteria.TestCaseUploadCriteria;
import com.base.meta.repository.*;
import com.base.meta.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/test-case-upload")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestCaseUploadController extends ABasicController {
    @Autowired
    TestCaseUploadRepository testCaseUploadRepository;
    @Autowired
    TestCaseUploadMapper testCaseUploadMapper;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProgramRepository programRepository;
    @Autowired
    TestCaseRepository testCaseRepository;
    @Autowired
    TestStepRepository testStepRepository;
    @Autowired
    ExcelService excelService;
//
//    @PostMapping(value="/create", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Transactional
//    @PreAuthorize("hasRole('TCU_C')")
//    public ApiMessageDto<TestCaseUploadDto> createTestCaseUpload(@Valid CreateTestCaseUploadForm createTestCaseUploadForm, BindingResult bindingResult) {
//        ApiMessageDto<TestCaseUploadDto> apiMessageDto = new ApiMessageDto<>();
//        if(!isTester()){
//            throw new UnauthorizationException("Not allowed create!");
//        }
//        Program program = programRepository.findById(createTestCaseUploadForm.getProgramId()).orElse(null);
//        if (program == null) {
//            throw new BadRequestException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
//        }
//        TestCaseUpload testCaseUpload = testCaseUploadMapper.fromCreateTestCaseUploadFormToEntity(createTestCaseUploadForm);
//        testCaseUpload.setProgram(program);
//        testCaseUploadRepository.save(testCaseUpload);
//        apiMessageDto.setData(testCaseUploadMapper.fromEntityToTestCaseUploadDto(testCaseUpload));
//        apiMessageDto.setMessage("Create test case upload success.");
//        return apiMessageDto;
//    }

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

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('TCU_D')")
    public ApiMessageDto<String> deleteTestCaseUpload(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
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

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestCaseUploadDto>> listTestCaseUpload(TestCaseUploadCriteria testCaseUploadCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestCaseUploadDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestCaseUpload> testCaseUploadPage = testCaseUploadRepository.findAll(testCaseUploadCriteria.getSpecification(), pageable);
        ResponseListDto<TestCaseUploadDto> responseListDto = new ResponseListDto(testCaseUploadMapper.fromEntityToTestCaseUploadDtoList(testCaseUploadPage.getContent()), testCaseUploadPage.getTotalElements(), testCaseUploadPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list test case upload success.");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('TCU_C')")
    public ApiMessageDto<String> uploadExcelFileToDb(@RequestParam("file") MultipartFile file) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed upload!");
        }
        if (file.isEmpty()) {
            apiMessageDto.setMessage("Please select a file to upload!");
            return apiMessageDto;
        }

        if (ExcelService.hasExcelFormat(file)) {
            try {
                List<TestCaseUpload> testCaseUploads = excelService.excelToTestCaseUploadData(file.getInputStream());
//                log.info("Upload test case upload: " + testCaseUploads.size());
                for (TestCaseUpload testCaseUpload : testCaseUploads) {
//                    log.info("Upload test case upload: " + testCaseUpload.getTestCaseName());
                    if (testCaseUpload.getProgram() == null) {
                        throw new BadRequestException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
                    }
                    testCaseUploadRepository.save(testCaseUpload);
                    if (testCaseRepository.findFirstByName(testCaseUpload.getTestCaseName()) == null) {
                        TestCase testcase = new TestCase();
                        testcase.setName(testCaseUpload.getTestCaseName());
                        testcase.setPrecondition(testCaseUpload.getTestCasePrecondition());
                        testcase.setMenuPath(testCaseUpload.getTestCaseMenuPath());
                        testcase.setProgram(testCaseUpload.getProgram());
                        testCaseRepository.save(testcase);
                        TestStep testStep = new TestStep();
                        testStep.setAction(testCaseUpload.getTestStepsAction());
                        testStep.setData(testCaseUpload.getTestStepsData());
                        testStep.setExpectedResult(testCaseUpload.getTestStepsExpectedResult());
                        testStep.setTestCase(testcase);
                        testStepRepository.save(testStep);
                    } else {
                        TestCase testcase = testCaseRepository.findFirstByName(testCaseUpload.getTestCaseName());
                        TestStep testStep = new TestStep();
                        testStep.setAction(testCaseUpload.getTestStepsAction());
                        testStep.setData(testCaseUpload.getTestStepsData());
                        testStep.setExpectedResult(testCaseUpload.getTestStepsExpectedResult());
                        testStep.setTestCase(testcase);
                        testStepRepository.save(testStep);
                    }
                }
                apiMessageDto.setMessage("Uploaded the file successfully: " + file.getOriginalFilename());
                return apiMessageDto;
            } catch (Exception e) {
                log.error(e.getMessage());
                apiMessageDto.setMessage("Could not upload the file: " + file.getOriginalFilename() + "!");
                return apiMessageDto;
            }
        }
        apiMessageDto.setMessage("Please upload an excel file!");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/reverse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> reverseTestCaseUpload(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isTester()) {
            throw new UnauthorizationException("Not allowed reverse!");
        }
        TestCaseUpload testCaseUpload = testCaseUploadRepository.findById(id).orElse(null);
        if (testCaseUpload == null) {
            throw new BadRequestException("Test case upload is not existed!", ErrorCode.TEST_CASE_UPLOAD_ERROR_NOT_EXIST);
        }
        TestCase testCase = testCaseRepository.findFirstByName(testCaseUpload.getTestCaseName());
        if (testCase == null) {
            throw new BadRequestException("Test case is not existed!", ErrorCode.TEST_CASE_ERROR_NOT_EXIST);
        }
        testCaseUploadRepository.delete(testCaseUpload);
        testCaseRepository.delete(testCase);
        apiMessageDto.setMessage("Reverse test case upload success.");
        return apiMessageDto;
    }
}
