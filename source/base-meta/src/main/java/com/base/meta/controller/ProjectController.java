package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.project.ProjectDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.ModifyFlagForm;
import com.base.meta.form.project.CreateProjectForm;
import com.base.meta.form.project.UpdateProjectForm;
import com.base.meta.mapper.ProjectMapper;
import com.base.meta.mapper.ProjectMemberMapper;
import com.base.meta.model.Category;
import com.base.meta.model.Project;
import com.base.meta.model.ProjectMember;
import com.base.meta.model.criteria.ProjectCriteria;
import com.base.meta.repository.AccountRepository;
import com.base.meta.repository.CategoryRepository;
import com.base.meta.repository.ProjectMemberRepository;
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
import java.util.Date;

@RestController
@RequestMapping("/v1/project")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProjectController extends ABasicController{
    private static final String PREFIX_ENTITY = "PJ";
    final ProjectMapper projectMapper;
    final ProjectRepository projectRepository;
    final BaseMetaApiService baseMetaApiService;
    final CategoryRepository categoryRepository;
    final AccountRepository accountRepository;
    final ProjectMemberRepository projectMemberRepository;
    final ProjectMemberMapper projectMemberMapper;

    public ProjectController(ProjectMapper projectMapper, ProjectRepository projectRepository, BaseMetaApiService baseMetaApiService, CategoryRepository categoryRepository, AccountRepository accountRepository, ProjectMemberRepository projectMemberRepository, ProjectMemberMapper projectMemberMapper) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.baseMetaApiService = baseMetaApiService;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.projectMemberMapper = projectMemberMapper;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PJ_C')")
    public ApiMessageDto<String> createProject(@Valid @RequestBody CreateProjectForm createProjectForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isPM()){
            throw new UnauthorizationException("Not allowed create!");
        }
        if (projectRepository.existsByName(createProjectForm.getName())) {
            throw new BadRequestException("Project name is existed!", ErrorCode.PROJECT_ERROR_NAME_DUPLICATED);
        }

        Category status = categoryRepository.findFirstById(createProjectForm.getStatusId());
        if (status == null) {
            throw new NotFoundException("Status is not existed!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }

        if (Boolean.FALSE.equals(baseMetaApiService.checkStartDateIsBeforeEndDate(createProjectForm.getStartDate(), createProjectForm.getEndDate()))) {
            throw new BadRequestException("Start date must be before end date!", ErrorCode.ERROR_DATE_INVALID);
        }

        Project project = projectMapper.fromCreateProjectFormToEntity(createProjectForm);
        project.setStatus(status);
        project.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        projectRepository.save(project);
        Long accountId = getCurrentUser();
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(project);
        projectMember.setAccount(accountRepository.findFirstById(accountId));
        projectMember.setOnBoardedDate(new Date());
        projectMemberRepository.save(projectMember);
        apiMessageDto.setMessage("Create a new project success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PJ_U')")
    public ApiMessageDto<String> updateProject(@Valid @RequestBody UpdateProjectForm updateProjectForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isPM()){
            throw new UnauthorizationException("Not allowed update!");
        }
        Project project = projectRepository.findById(updateProjectForm.getId()).orElseThrow(()
                -> new NotFoundException("Project is not existed!", ErrorCode.PROJECT_ERROR_NOT_EXIST));
        if(projectRepository.existsByName(updateProjectForm.getName()) && !project.getName().equals(updateProjectForm.getName())){
            throw new BadRequestException("Project name is existed!", ErrorCode.PROJECT_ERROR_NAME_DUPLICATED);
        }
        if (Boolean.FALSE.equals(baseMetaApiService.checkStartDateIsBeforeEndDate(updateProjectForm.getStartDate(), updateProjectForm.getEndDate()))) {
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
        if (!isPM()) {
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

    @PutMapping(value = "/update-flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PJ_UF')")
    public ApiMessageDto<String> updateFlagProject(@RequestBody ModifyFlagForm modifyFlagForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isPM()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        Project project = projectRepository.findById(modifyFlagForm.getObjectId()).orElseThrow(()
                -> new NotFoundException("Project is not existed!", ErrorCode.PROJECT_ERROR_NOT_EXIST));
        project.setFlag(modifyFlagForm.getFlag());
        projectRepository.save(project);
        apiMessageDto.setMessage("Update project flag success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<ProjectDto>> listProject(ProjectCriteria projectCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ProjectDto>> apiMessageDto = new ApiMessageDto<>();
        projectCriteria.setFlag(BaseMetaConstant.STATUS_ACTIVE);
        Page<Project> project = projectRepository.findAll(projectCriteria.getSpecification(), pageable);
        ResponseListDto<ProjectDto> responseListDto = new ResponseListDto(projectMapper.fromEntityToProjectDtoList(project.getContent()), project.getTotalElements(), project.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List projects success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProjectDto> getProject(@PathVariable Long id) {
        ApiMessageDto<ProjectDto> apiMessageDto = new ApiMessageDto<>();
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project is not existed!", ErrorCode.PROJECT_ERROR_NOT_EXIST));
        apiMessageDto.setData(projectMapper.fromEntityToProjectDto(project));
        apiMessageDto.setMessage("Get project success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-by-pm-account", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('PJ_LBPMA')")
    public ApiMessageDto<ResponseListDto<ProjectDto>> listProjectByPmAccount(Pageable pageable) {
        if(!isPM()){
            throw new UnauthorizationException("Not allowed list!");
        }
        ApiMessageDto<ResponseListDto<ProjectDto>> apiMessageDto = new ApiMessageDto<>();
        Long accountId = getCurrentUser();
        Page<ProjectMember> project = projectMemberRepository.findAllByAccountId(accountId, pageable);
        ResponseListDto responseListDto = new ResponseListDto(projectMemberMapper.fromEntityToProjectMemberDtoList(project.getContent()), project.getTotalElements(), project.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List projects by PM account success.");
        return apiMessageDto;
    }

}
