package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.project.CreateProjectForm;
import com.base.meta.form.project.UpdateProjectForm;
import com.base.meta.mapper.ProjectMapper;
import com.base.meta.model.Category;
import com.base.meta.model.Project;
import com.base.meta.model.criteria.ProjectCriteria;
import com.base.meta.repository.CategoryRepository;
import com.base.meta.repository.ProjectRepository;
import com.base.meta.service.BaseMetaApiService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
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
@RequestMapping("/v1/project")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProjectController extends ABasicController{
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    BaseMetaApiService baseMetaApiService;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PJ_C')")
    public ApiMessageDto<String> createProject(@Valid @RequestBody CreateProjectForm createProjectForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isPM() && !isSuperAdmin()){
            throw new UnauthorizationException("Not allowed create!");
        }
        Project project = projectRepository.findFirstByName(createProjectForm.getName());

        Category status = categoryRepository.findFirstById(createProjectForm.getStatusId());
        if (status == null) {
            throw new NotFoundException("Status is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        if (project != null) {
            throw new BadRequestException("Project name is existed!", ErrorCode.PROJECT_ERROR_NAME_DUPLICATED);
        }
        if(!baseMetaApiService.checkEndDateIsAfterNow(createProjectForm.getEndDate())){
            throw new BadRequestException("End date must be after now!", ErrorCode.ERROR_DATE_INVALID);
        }
        if (!baseMetaApiService.checkStartDateIsBeforeEndDate(createProjectForm.getStartDate(), createProjectForm.getEndDate())) {
            throw new BadRequestException("Start date must be before end date!", ErrorCode.ERROR_DATE_INVALID);
        }
        if (!baseMetaApiService.checkStartDateIsAfterNow(createProjectForm.getStartDate())) {
            throw new BadRequestException("Start date must be after now!", ErrorCode.ERROR_DATE_INVALID);
        }
        project = projectMapper.fromCreateProjectFormToEntity(createProjectForm);
        project.setStatus(status);
        projectRepository.save(project);
        apiMessageDto.setMessage("Create a new project success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PJ_U')")
    public ApiMessageDto<String> updateProject(@Valid @RequestBody UpdateProjectForm updateProjectForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isPM() && !isSuperAdmin()){
            throw new UnauthorizationException("Not allowed update!");
        }
        Project project = projectRepository.findById(updateProjectForm.getId()).orElse(null);
        if (project == null) {
            throw new NotFoundException("Project is not existed!", ErrorCode.PROJECT_ERROR_NOT_EXIST);
        }
        Project projectCheck = projectRepository.findFirstByName(updateProjectForm.getName());
        if(projectCheck != null && !projectCheck.getId().equals(project.getId())){
            throw new BadRequestException("Project name is existed!", ErrorCode.PROJECT_ERROR_NAME_DUPLICATED);
        }
        if (!baseMetaApiService.checkStartDateIsBeforeEndDate(updateProjectForm.getStartDate(), updateProjectForm.getEndDate())) {
            throw new BadRequestException("Start date must be before end date!", ErrorCode.ERROR_DATE_INVALID);
        }

        Category status = categoryRepository.findFirstById(updateProjectForm.getStatusId());
        if (status == null) {
            throw new NotFoundException("Status is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        projectMapper.updateProjectFromEntity(updateProjectForm, project);
        project.setStatus(status);
        projectRepository.save(project);
        apiMessageDto.setMessage("Update project success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PJ_D')")
    public ApiMessageDto<String> deleteProject(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isPM() && !isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null) {
            throw new BadRequestException("Project is not existed!", ErrorCode.PROJECT_ERROR_NOT_EXIST);
        }
        projectRepository.delete(project);
        apiMessageDto.setMessage("Delete project success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<ProjectDto>> listProject(ProjectCriteria projectCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ProjectDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Project> project = projectRepository.findAll(projectCriteria.getSpecification(), pageable);
        ResponseListDto<ProjectDto> responseListDto = new ResponseListDto(projectMapper.fromEntityToProjectDtoList(project.getContent()), project.getTotalElements(), project.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List projects success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProjectDto> getProject(@PathVariable Long id) {
        ApiMessageDto<ProjectDto> apiMessageDto = new ApiMessageDto<>();
        Project project = projectRepository.findById(id).orElseThrow(() -> new BadRequestException("Project is not existed!", ErrorCode.PROJECT_ERROR_NOT_EXIST));
        apiMessageDto.setData(projectMapper.fromEntityToProjectDto(project));
        apiMessageDto.setMessage("Get project success.");
        return apiMessageDto;
    }

}
