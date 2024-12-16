package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.projectmember.ProjectMemberDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.projectmember.CreateProjectMemberForm;
import com.base.meta.form.projectmember.UpdateProjectMemberForm;
import com.base.meta.mapper.ProjectMemberMapper;
import com.base.meta.model.Account;
import com.base.meta.model.Project;
import com.base.meta.model.ProjectMember;
import com.base.meta.model.criteria.ProjectMemberCriteria;
import com.base.meta.repository.AccountRepository;
import com.base.meta.repository.CategoryRepository;
import com.base.meta.repository.ProjectMemberRepository;
import com.base.meta.repository.ProjectRepository;
import com.base.meta.service.BaseMetaApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/project-member")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProjectMemberController extends ABasicController{
    @Autowired
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    ProjectMemberMapper projectMemberMapper;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BaseMetaApiService baseMetaApiService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PM_C')")
    @Transactional
    public ApiMessageDto<String> createProjectMember(@Valid @RequestBody CreateProjectMemberForm createProjectMemberForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), ErrorCode.PROJECT_MEMBER_ERROR_INVALID);
        }
        if (!isPM()) {
            throw new UnauthorizationException("Not allowed create!");
        }
        Project project = projectRepository.findById(createProjectMemberForm.getProjectId()).orElse(null);
        if (project == null) {
            throw new NotFoundException("Project is not existed!", ErrorCode.PROJECT_ERROR_NOT_EXIST);
        }
        for (Long accountId : createProjectMemberForm.getAccountIds()) {
            Account account = accountRepository.findById(accountId).orElseThrow(()
                    -> new NotFoundException("Account is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND));

            if (!(Integer.parseInt(account.getStatus().getCode()) == BaseMetaConstant.USER_STATUS_AVAILABLE || Integer.parseInt(account.getStatus().getCode()) == BaseMetaConstant.USER_STATUS_BUSY)
                    && account.getStatus().getKind().equals(BaseMetaConstant.CATEGORY_KIND_ACCOUNT)) {
                throw new BadRequestException("Account is not available!", ErrorCode.USER_ERROR_NOT_AVAILABLE);
            }

            ProjectMember projectMember = new ProjectMember();

            account.setStatus(categoryRepository.findFirstByCodeAndKind(String.valueOf(BaseMetaConstant.USER_STATUS_BUSY),
                    BaseMetaConstant.CATEGORY_KIND_ACCOUNT));
            accountRepository.save(account);

            projectMember.setProject(project);
            projectMember.setAccount(account);
            projectMember.setOnBoardedDate(new Date());
            projectMemberRepository.save(projectMember);
        }
        apiMessageDto.setMessage("Create project member success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PM_U')")
    @Transactional
    public ApiMessageDto<String> updateProjectMember(@Valid @RequestBody UpdateProjectMemberForm updateProjectMemberForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(!isPM()){
            throw new UnauthorizationException("Not allowed update!");
        }
        ProjectMember projectMember = projectMemberRepository.findById(updateProjectMemberForm.getId()).orElse(null);
        if (projectMember == null) {
            throw new BadRequestException("Project member is not existed!", ErrorCode.PROJECT_MEMBER_ERROR_NOT_EXIST);
        }
        projectMemberMapper.updateProjectMemberFromEntity(updateProjectMemberForm, projectMember);
        projectMemberRepository.save(projectMember);
        apiMessageDto.setMessage("Update project member success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('PM_D')")
    public ApiMessageDto<String> deleteProjectMember(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (!isPM()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        ProjectMember projectMember = projectMemberRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Project member is not existed!", ErrorCode.PROJECT_MEMBER_ERROR_NOT_EXIST));

        Account account = projectMember.getAccount();
        account.setStatus(categoryRepository.findFirstByCodeAndKind(String.valueOf(BaseMetaConstant.USER_STATUS_AVAILABLE), BaseMetaConstant.CATEGORY_KIND_ACCOUNT));
        accountRepository.save(account);

        projectMemberRepository.delete(projectMember);
        apiMessageDto.setMessage("Delete project member success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<ProjectMemberDto>> listProjectMembers(ProjectMemberCriteria projectMemberCriteria, @PageableDefault(size = 1000) Pageable pageable) {
        ApiMessageDto<ResponseListDto<ProjectMemberDto>> apiMessageDto = new ApiMessageDto<>();
        Page<ProjectMember> page = projectMemberRepository.findAll(projectMemberCriteria.getSpecification(), pageable);
        ResponseListDto<ProjectMemberDto> responseListDto = new ResponseListDto(projectMemberMapper.fromEntityToProjectMemberDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List project members success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProjectMemberDto> getProjectMember(@PathVariable Long id) {
        ApiMessageDto<ProjectMemberDto> apiMessageDto = new ApiMessageDto<>();
        ProjectMember projectMember = projectMemberRepository.findById(id).orElse(null);
        if (projectMember == null) {
            throw new BadRequestException("Project member is not existed!", ErrorCode.PROJECT_MEMBER_ERROR_NOT_EXIST);
        }
        apiMessageDto.setData(projectMemberMapper.fromEntityToProjectMemberDto(projectMember));
        apiMessageDto.setMessage("Get project member success.");
        return apiMessageDto;
    }
}
