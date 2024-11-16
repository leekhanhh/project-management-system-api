package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.ModifyFlagForm;
import com.base.meta.form.program.CreateProgramForm;
import com.base.meta.form.program.UpdateProgramForm;
import com.base.meta.mapper.ProgramMapper;
import com.base.meta.model.*;
import com.base.meta.model.criteria.ProgramCriteria;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/program")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProgramController extends ABasicController {
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
        Program program = programRepository.findFirstByName(createProgramForm.getName());
        if (program != null) {
            throw new NotFoundException("Program name is existed!", ErrorCode.PROGRAM_ERROR_NAME_EXIST);
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

        if (!baseMetaApiService.checkStartDateIsBeforeEndDate(createProgramForm.getStartDate(), createProgramForm.getEndDate())) {
            throw new BadRequestException("Start date must be before end date!", ErrorCode.ERROR_DATE_INVALID);
        }

        program = programMapper.fromCreateProgramFormToEntity(createProgramForm);
        program.setProject(project);
        program.setRequirement(requirement);
        program.setProgramOwner(programOwner);
        program.setAssignedDeveloper(developer);
        program.setAssignedTester(tester);
        program.setProgramType(programType);
        program.setProgramStatus(programStatus);
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
        Program programCheck = programRepository.findFirstByName(updateProgramForm.getName());
        if (programCheck != null && !programCheck.getId().equals(program.getId())) {
            throw new NotFoundException("Program name is existed!", ErrorCode.PROGRAM_ERROR_NAME_EXIST);
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

        if (!baseMetaApiService.checkStartDateIsBeforeEndDate(updateProgramForm.getStartDate(), updateProgramForm.getEndDate())) {
            throw new BadRequestException("Start date must be before end date!", ErrorCode.ERROR_DATE_INVALID);
        }

        programMapper.fromUpdateFormToEntity(updateProgramForm, program);
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

}
