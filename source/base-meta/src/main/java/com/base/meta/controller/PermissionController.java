package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.permission.PermissionDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.form.permission.CreatePermissionForm;
import com.base.meta.mapper.PermissionMapper;
import com.base.meta.model.Permission;
import com.base.meta.model.criteria.PermissionCriteria;
import com.base.meta.repository.GroupRepository;
import com.base.meta.repository.PermissionRepository;
import com.base.meta.exception.UnauthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.base.meta.dto.ErrorCode;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/permission")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PermissionController extends ABasicController {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    GroupRepository groupRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PER_C')")
    public ApiMessageDto<String> createPermission(@Valid @RequestBody CreatePermissionForm createPermissionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Permission permission = permissionRepository.findFirstByName(createPermissionForm.getName());
        if (permission != null) {
            throw new BadRequestException("Permission name is existed!", ErrorCode.PERMISSION_ERROR_NAME_EXIST);
        }
        boolean permissionCodeExist = permissionRepository.existsByPermissionCode(createPermissionForm.getPermissionCode());
        if (permissionCodeExist) {
            throw new BadRequestException("Permission code is existed!", ErrorCode.PERMISSION_ERROR_CODE_EXIST);
        }
        permission = permissionMapper.fromCreatePermissionFormToEntity(createPermissionForm);
        permissionRepository.save(permission);
        apiMessageDto.setMessage("Create a new permission success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PER_L')")
    public ApiMessageDto<ResponseListDto<PermissionDto>> listPermissions(PermissionCriteria permissionCriteria, @PageableDefault(size = 1000) Pageable pageable) {
        if ((permissionCriteria.getKind() == null && !isSuperAdmin())
                || (permissionCriteria.getKind() != null && permissionCriteria.getKind().equals(BaseMetaConstant.PERMISSION_KIND_SYSTEM) && !isSuperAdmin())
                || (permissionCriteria.getKind() != null && !permissionCriteria.getKind().equals(BaseMetaConstant.PERMISSION_KIND_SYSTEM) && (!isDev() && !isSuperAdmin() && !isTester()))) {
            throw new UnauthorizationException("Not allowed list!");
        }
        ApiMessageDto<ResponseListDto<PermissionDto>> apiMessageDto = new ApiMessageDto<>();
        /*Page<Permission> accounts = permissionRepository.findAll(PageRequest.of(0, 1000, Sort.by(new Sort.Order(Sort.Direction.DESC, "createdDate"))));
        apiMessageDto.setData(accounts.getContent());*/
        Page<Permission> page = permissionRepository.findAll(permissionCriteria.getSpecification(), pageable);
        ResponseListDto<PermissionDto> responseListDto = new ResponseListDto(permissionMapper.fromEntityToPermissionDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List permissions success.");
        return apiMessageDto;
    }

    /*@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PER_D')")
    @Transactional
    public ApiMessageDto<String> deletePermission(@PathVariable("id") Long id) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed delete.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Permission permission = permissionRepository.findById(id).orElse(null);
        if (permission == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.PERMISSION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Permission not found");
            return apiMessageDto;
        }
        if (permission.getGroups() != null && !permission.getGroups().isEmpty()) {
            for (Group group : permission.getGroups()) {
                group.getPermissions().removeIf(p -> p.getId().equals(permission.getId()));
            }
            groupRepository.saveAll(permission.getGroups());
        }
        permissionRepository.deleteById(id);
        apiMessageDto.setMessage("Delete permission success");
        return apiMessageDto;
    }*/
}
