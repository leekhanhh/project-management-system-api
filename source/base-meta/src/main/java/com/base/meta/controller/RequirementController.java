package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.requirement.CreateRequirementForm;
import com.base.meta.form.requirement.UpdateRequirementForm;
import com.base.meta.mapper.RequirementMapper;
import com.base.meta.model.Category;
import com.base.meta.model.Program;
import com.base.meta.model.Project;
import com.base.meta.model.Requirement;
import com.base.meta.model.criteria.RequirementCriteria;
import com.base.meta.repository.CategoryRepository;
import com.base.meta.repository.ProjectRepository;
import com.base.meta.repository.RequirementRepository;
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
@RequestMapping("/v1/requirement")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class RequirementController extends ABasicController {
    @Autowired
    RequirementRepository requirementRepository;
    @Autowired
    RequirementMapper requirementMapper;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('RQ_C')")
    public ApiMessageDto<String> createRequirement(@Valid @RequestBody CreateRequirementForm createRequirementForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed create!");
        }
        Requirement requirement = requirementRepository.findFirstByName(createRequirementForm.getName());
        if (requirement != null) {
            throw new BadRequestException("Requirement name is existed!", ErrorCode.REQUIREMENT_ERROR_NAME_EXISTED);
        }
        Project project = projectRepository.findById(createRequirementForm.getProjectId()).orElse(null);
        if (project == null) {
            throw new NotFoundException("Project is not existed!", ErrorCode.PROJECT_ERROR_NOT_EXIST);
        }

        Category devision = categoryRepository.findById(createRequirementForm.getDevisionId()).orElseThrow(()
                -> new NotFoundException("Devision is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        Category acceptance = categoryRepository.findById(createRequirementForm.getAcceptanceId()).orElseThrow(()
                -> new NotFoundException("Acceptance is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        Category detailClassification = categoryRepository.findById(createRequirementForm.getClassificationId()).orElseThrow(()
                -> new NotFoundException("Classification is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        requirement = requirementMapper.fromCreateRequirementFormToEntity(createRequirementForm);
        requirement.setProject(project);
        requirement.setDevision(devision);
        requirement.setAcceptance(acceptance);
        requirement.setDetailClassification(detailClassification);
        requirementRepository.save(requirement);
        apiMessageDto.setMessage("Create a new requirement success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('RQ_U')")
    public ApiMessageDto<String> updateRequirement(@Valid @RequestBody UpdateRequirementForm updateRequirementForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed update!");
        }
        Requirement requirement = requirementRepository.findFirstById(updateRequirementForm.getId());
        if (requirement == null) {
            throw new NotFoundException("Requirement is not existed!", ErrorCode.REQUIREMENT_ERROR_NOT_FOUND);
        }
        Category devision = categoryRepository.findById(updateRequirementForm.getDevisionId()).orElseThrow(()
                -> new NotFoundException("Devision is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        Category acceptance = categoryRepository.findById(updateRequirementForm.getAcceptanceId()).orElseThrow(()
                -> new NotFoundException("Acceptance is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        Category detailClassification = categoryRepository.findById(updateRequirementForm.getDetailClassificationId()).orElseThrow(()
                -> new NotFoundException("Classification is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        requirementMapper.fromUpdateRequirementFormToEntity(updateRequirementForm, requirement);
        requirement.setDevision(devision);
        requirement.setAcceptance(acceptance);
        requirement.setDetailClassification(detailClassification);
        requirementRepository.save(requirement);
        apiMessageDto.setMessage("Update a requirement success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('RQ_D')")
    public ApiMessageDto<String> deleteRequirement(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        Requirement requirement = requirementRepository.findById(id).orElse(null);
        if (requirement == null) {
            throw new BadRequestException("Requirement is not existed!", ErrorCode.REQUIREMENT_ERROR_NOT_FOUND);
        }
        requirementRepository.delete(requirement);
        apiMessageDto.setMessage("Delete a requirement success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<RequirementDto>> listRequirement(RequirementCriteria requirementCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<RequirementDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Requirement> requirementPage = requirementRepository.findAll(requirementCriteria.getSpecification(), pageable);
        ResponseListDto<RequirementDto> responseListDto = new ResponseListDto(requirementMapper.fromEntityToRequirementDtoList(requirementPage.getContent()), requirementPage.getTotalElements(), requirementPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List requirements success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<RequirementDto> getRequirement(@PathVariable("id") Long id) {
        ApiMessageDto<RequirementDto> apiMessageDto = new ApiMessageDto<>();
        Requirement requirement = requirementRepository.findById(id).orElse(null);
        if (requirement == null) {
            throw new BadRequestException("Requirement is not existed!", ErrorCode.REQUIREMENT_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(requirementMapper.fromEntityToRequirementDto(requirement));
        apiMessageDto.setMessage("Get a requirement success.");
        return apiMessageDto;
    }
}
