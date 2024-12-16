package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.ModifyFlagForm;
import com.base.meta.form.program.CreateProgramForm;
import com.base.meta.form.program.ProgramUploadForm;
import com.base.meta.form.program.UpdateProgramForm;
import com.base.meta.mapper.ProgramMapper;
import com.base.meta.model.*;
import com.base.meta.model.criteria.ProgramCriteria;
import com.base.meta.repository.*;
import com.base.meta.service.BaseMetaApiService;
import com.base.meta.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/program")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProgramController extends ABasicController {
    private static final String PREFIX_ENTITY = "PG";
    @Autowired
    ProgramRepository programRepository;
    @Autowired
    ProgramMapper programMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    RequirementRepository requirementRepository;
    @Autowired
    ExcelService excelService;
    @Autowired
    BaseMetaApiService baseMetaApiService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PG_C')")
    public ApiMessageDto<String> createProgram(@Valid @RequestBody CreateProgramForm createProgramForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isPM()) {
            throw new UnauthorizationException("Not allowed create!");
        }

        Project project = projectRepository.findFirstById(createProgramForm.getProjectId());
        if (project == null) {
            throw new NotFoundException("Project is not existed!", ErrorCode.PROJECT_ERROR_NOT_EXIST);
        }

        Requirement requirement = requirementRepository.findFirstById(createProgramForm.getRequirementId());
        if (requirement == null) {
            throw new NotFoundException("Requirement is not existed!", ErrorCode.REQUIREMENT_ERROR_NOT_FOUND);
        }

        if (programRepository.existsProgramByNameAndProjectId(createProgramForm.getName(), createProgramForm.getProjectId())) {
            throw new BadRequestException("Program name is existed!", ErrorCode.PROGRAM_ERROR_NAME_EXIST);
        }

        Account programOwner = accountRepository.findFirstById(createProgramForm.getProgramOwnerId());
        if (programOwner == null) {
            throw new NotFoundException("Program owner is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }

        Account developer = accountRepository.findFirstById(createProgramForm.getDeveloperId());
        if (developer == null) {
            throw new NotFoundException("Developer is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }

        Account tester = accountRepository.findFirstById(createProgramForm.getTesterId());
        if (tester == null) {
            throw new NotFoundException("Tester is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }

        Category programType = categoryRepository.findFirstById(createProgramForm.getProgramTypeId());
        if (programType == null) {
            throw new NotFoundException("Program type is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        Category programStatus = categoryRepository.findFirstById(createProgramForm.getStatusId());
        if (programStatus == null) {
            throw new NotFoundException("Program status is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        if (Boolean.FALSE.equals(baseMetaApiService.checkStartDateIsBeforeEndDate(createProgramForm.getStartDate(), createProgramForm.getEndDate()))) {
            throw new BadRequestException("Start date must be before end date!", ErrorCode.ERROR_DATE_INVALID);
        }

        Program program = programMapper.fromCreateProgramFormToEntity(createProgramForm);
        program.setProject(project);
        program.setRequirement(requirement);
        program.setProgramOwner(programOwner);
        program.setAssignedDeveloper(developer);
        program.setAssignedTester(tester);
        program.setProgramType(programType);
        program.setProgramStatus(programStatus);
        program.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        programRepository.save(program);
        apiMessageDto.setMessage("Create a new program success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PG_U')")
    public ApiMessageDto<ProgramDto> updateProgram(@Valid @RequestBody UpdateProgramForm updateProgramForm, BindingResult bindingResult) {
        ApiMessageDto<ProgramDto> apiMessageDto = new ApiMessageDto<>();
        if (!isPM()) {
            throw new UnauthorizationException("Not allowed update!");
        }
        Program program = programRepository.findFirstById(updateProgramForm.getId());
        if (program == null) {
            throw new NotFoundException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
        }

        Requirement requirement = requirementRepository.findFirstById(updateProgramForm.getRequirementId());
        if (requirement == null) {
            throw new NotFoundException("Requirement is not existed!", ErrorCode.REQUIREMENT_ERROR_NOT_FOUND);
        }

        if (updateProgramForm.getName() != program.getName()) {
            if (programRepository.existsProgramByNameAndProjectId(updateProgramForm.getName(), program.getProject().getId())) {
                throw new BadRequestException("Program name is existed!", ErrorCode.PROGRAM_ERROR_NAME_EXIST);
            }
        }

        Account programOwner = accountRepository.findFirstById(updateProgramForm.getOwnerId());
        if (programOwner == null) {
            throw new NotFoundException("Program owner is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }

        Account developer = accountRepository.findFirstById(updateProgramForm.getDeveloperId());
        if (developer == null) {
            throw new NotFoundException("Developer is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }

        Account tester = accountRepository.findFirstById(updateProgramForm.getTesterId());
        if (tester == null) {
            throw new NotFoundException("Tester is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }

        Category programType = categoryRepository.findFirstById(updateProgramForm.getProgramTypeId());
        if (programType == null) {
            throw new NotFoundException("Program type is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        Category programStatus = categoryRepository.findFirstById(updateProgramForm.getStatusId());
        if (programStatus == null) {
            throw new NotFoundException("Program status is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        if (Boolean.FALSE.equals(baseMetaApiService.checkStartDateIsBeforeEndDate(updateProgramForm.getStartDate(), updateProgramForm.getEndDate()))) {
            throw new BadRequestException("Start date must be before end date!", ErrorCode.ERROR_DATE_INVALID);
        }

        programMapper.fromUpdateFormToEntity(updateProgramForm, program);
        program.setRequirement(requirement);
        program.setProgramOwner(programOwner);
        program.setAssignedDeveloper(developer);
        program.setAssignedTester(tester);
        program.setProgramType(programType);
        program.setProgramStatus(programStatus);
        programRepository.save(program);
        apiMessageDto.setMessage("Update program success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProgramDto> getProgram(@PathVariable Long id) {
        ApiMessageDto<ProgramDto> apiMessageDto = new ApiMessageDto<>();
        Program program = programRepository.findFirstById(id);
        if (program == null) {
            throw new BadRequestException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
        }
        ProgramDto programDto = programMapper.fromDtoToEntity(program);
        apiMessageDto.setData(programDto);
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<ProgramDto>> programList(ProgramCriteria programCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ProgramDto>> apiMessageDto = new ApiMessageDto<>();
        programCriteria.setStatus(BaseMetaConstant.STATUS_ACTIVE);
        Page<Program> programPage = programRepository.findAll(programCriteria.getSpecification(), pageable);
        ResponseListDto<ProgramDto> responseListDto = new ResponseListDto(programMapper.fromEntityToProgramDtoList(programPage.getContent()), programPage.getTotalElements(), programPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PG_D')")
    public ApiMessageDto<String> deleteProgram(@PathVariable Long id) {
        if (!isPM()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Program program = programRepository.findById(id).orElse(null);
        if (program == null) {
            throw new BadRequestException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
        }
        programRepository.delete(program);
        apiMessageDto.setMessage("Delete program success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PG_UF')")
    public ApiMessageDto<String> updateFlagProgram(@RequestBody ModifyFlagForm modifyFlagForm) {
        if (!isPM()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Program program = programRepository.findById(modifyFlagForm.getObjectId()).orElse(null);
        if (program == null) {
            throw new BadRequestException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST);
        }
        program.setFlag(modifyFlagForm.getFlag());
        programRepository.save(program);
        apiMessageDto.setMessage("Update program flag success.");
        return apiMessageDto;
    }

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('PG_UPF')")
    @Transactional
    public ApiMessageDto<String> uploadProgramExcelFileToDb(@RequestParam("file") MultipartFile file) {
        if (!isPM()) {
            throw new UnauthorizationException("Not allowed upload!");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (file.isEmpty()) {
            apiMessageDto.setMessage("Please select a file to upload!");
            return apiMessageDto;
        }

        if (excelService.hasExcelFormat(file)) {
            try {
                List<ProgramUploadForm> programUploadForms = excelService.mapExcelToProgramData(file.getInputStream());
                for (ProgramUploadForm programUploadForm : programUploadForms) {
                    StringBuilder message = new StringBuilder();
                    Project project = projectRepository.findFirstById(Long.parseLong(programUploadForm.getProjectId()));
                    if (project == null) {
                        message.append("Project is not existed!").append("Project id: ").append(programUploadForm.getProjectId());
                        throw new NotFoundException(message.toString(), ErrorCode.PROJECT_ERROR_NOT_EXIST);
                    }
                    Requirement requirement = requirementRepository.findFirstById(Long.parseLong(programUploadForm.getRequirementId()));
                    if (requirement == null) {
                        message.append("Requirement is not existed!").append(" Requirement id: ").append(programUploadForm.getRequirementId());
                        throw new NotFoundException(message.toString(), ErrorCode.REQUIREMENT_ERROR_NOT_FOUND);
                    }
                    if (programRepository.existsProgramByNameAndProjectId(programUploadForm.getProgramName(), project.getId())) {
                        message.append("Program name is existed!").append(" Program name: ").append(programUploadForm.getProgramName());
                        message.append("Project name: ").append(project.getName());
                        throw new NotFoundException(message.toString(), ErrorCode.PROGRAM_ERROR_NAME_EXIST);
                    }
                    Boolean isStartDateBeforeEndDate = baseMetaApiService.checkStartDateIsBeforeEndDate(programUploadForm.getStartDate(), programUploadForm.getEndDate());
                    if (!isStartDateBeforeEndDate) {
                        message.append("Start date must be before end date!").append(" Start date: ").append(programUploadForm.getStartDate()).append(" End date: ").append(programUploadForm.getEndDate()).append(".");
                        throw new BadRequestException(message.toString(), ErrorCode.ERROR_DATE_INVALID);
                    }
                    Category programType = categoryRepository.findByNameAndKind(programUploadForm.getProgramType(), BaseMetaConstant.CATEGORY_KIND_PROGRAM);
                    if (programType == null) {
                        message.append("Program type is not existed!").append(" Program type: ").append(programUploadForm.getProgramType());
                        message.append("Project id: ").append(programUploadForm.getProjectId());
                        throw new NotFoundException(message.toString(), ErrorCode.CATEGORY_ERROR_NOT_FOUND);
                    }
                    Program program = new Program();
                    program.setProject(project);
                    program.setRequirement(requirement);
                    program.setProgramCategory(programUploadForm.getProgramCategory());
                    program.setProgramType(programType);
                    program.setStartDate(programUploadForm.getStartDate());
                    program.setEndDate(programUploadForm.getEndDate());
                    program.setDescription(programUploadForm.getDescription());
                    program.setName(programUploadForm.getProgramName());
                    programRepository.save(program);
                }
                apiMessageDto.setMessage("Uploaded the file successfully: " + file.getOriginalFilename());
            } catch (Exception e) {
                throw new BadRequestException("Fail to parse Excel file!", e.getMessage());
            }
        } else {
            throw new BadRequestException("Please upload an excel file!");
        }
        return apiMessageDto;
    }
}
