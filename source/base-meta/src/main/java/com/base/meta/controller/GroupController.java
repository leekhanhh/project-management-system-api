package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.group.GroupDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.group.AddPermissionForm;
import com.base.meta.form.group.CreateGroupForm;
import com.base.meta.form.group.UpdateGroupForm;
import com.base.meta.mapper.GroupMapper;
import com.base.meta.model.Group;
import com.base.meta.model.Permission;
import com.base.meta.model.criteria.GroupCriteria;
import com.base.meta.repository.GroupRepository;
import com.base.meta.repository.PermissionRepository;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.service.BaseMetaApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.base.meta.dto.ErrorCode;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/group")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class GroupController extends ABasicController {
    private static final String PREFIX_ENTITY = "GR";
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    BaseMetaApiService baseMetaApiService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateGroupForm createGroupForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed create!");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findFirstByName(createGroupForm.getName());
        if (group != null) {
            throw new BadRequestException("Group name is exist!", ErrorCode.GROUP_ERROR_NAME_EXIST);
        }
        group = groupRepository.findFirstByKind(createGroupForm.getKind());
        if (group != null) {
            throw new BadRequestException("Group kind is exist!", ErrorCode.GROUP_ERROR_KIND_EXIST);
        }
        group = groupMapper.fromCreateGroupFormToEntity(createGroupForm);
        List<Permission> permissions = new ArrayList<>();
        for (long permissionId : createGroupForm.getPermissions()) {
            Permission permission = permissionRepository.findById(permissionId).orElse(null);
            if (permission != null) {
                permissions.add(permission);
            }
        }
        group.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        group.setPermissions(permissions);
        groupRepository.save(group);
        apiMessageDto.setMessage("Create a new group success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_U')")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateGroupForm updateGroupForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed update!");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(updateGroupForm.getId()).orElse(null);
        if (group == null) {
            throw new NotFoundException("Group not found!", ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        if (StringUtils.isNoneBlank(updateGroupForm.getName())) {
            Group otherGroup = groupRepository.findFirstByName(updateGroupForm.getName());
            if (otherGroup != null && !Objects.equals(updateGroupForm.getId(), otherGroup.getId())) {
                throw new BadRequestException("Cant update this group name because it is exist!", ErrorCode.GROUP_ERROR_NAME_EXIST);
            }
            group.setName(updateGroupForm.getName());
        }
        if (StringUtils.isNoneBlank(updateGroupForm.getDescription())) {
            group.setDescription(updateGroupForm.getDescription());
        }
        List<Permission> permissions = new ArrayList<>();
        for (long permissionId : updateGroupForm.getPermissions()) {
            Permission permission = permissionRepository.findById(permissionId).orElse(null);
            if (permission != null) {
                permissions.add(permission);
            }
        }
        group.setPermissions(permissions);
        groupRepository.save(group);
        apiMessageDto.setMessage("Update group success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_V')")
    public ApiMessageDto<GroupDto> get(@PathVariable("id") Long id) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed to get!");
        }
        ApiMessageDto<GroupDto> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            throw new NotFoundException("Group not found!", ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(groupMapper.fromEntityToGroupDto(group));
        apiMessageDto.setMessage("Get group success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_L')")
    public ApiMessageDto<ResponseListDto<GroupDto>> list(GroupCriteria groupCriteria, Pageable pageable) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed list group!");
        }
        ApiMessageDto<ResponseListDto<GroupDto>> apiMessageDto = new ApiMessageDto<>();
        groupCriteria.setStatus(BaseMetaConstant.STATUS_ACTIVE);
        Page<Group> groups = groupRepository.findAll(groupCriteria.getSpecification(), pageable);
        ResponseListDto<GroupDto> responseListDto = new ResponseListDto(groupMapper.fromEntityToGroupDtoList(groups.getContent()), groups.getTotalElements(), groups.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List group success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/add-permission", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_AP')")
    @Transactional
    public ApiMessageDto<String> addPermission(@Valid @RequestBody AddPermissionForm addPermissionForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed add permission!");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(addPermissionForm.getId()).orElse(null);
        if (group == null) {
            throw new NotFoundException("Group not found!", ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        List<Permission> permissionList = new ArrayList<>();
        for (long permissionId : addPermissionForm.getPermissions()) {
            Permission permission = permissionRepository.findById(permissionId).orElse(null);
            if (permission != null) {
                permissionList.add(permission);
            }
        }
        group.getPermissions().addAll(permissionList);
        groupRepository.save(group);
        apiMessageDto.setMessage("Add permission success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/remove-permission", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_RP')")
    @Transactional
    public ApiMessageDto<String> removePermission(@Valid @RequestBody AddPermissionForm addPermissionForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed remove permission.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(addPermissionForm.getId()).orElse(null);
        if (group == null) {
            throw new NotFoundException("Group not found!", ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        List<Permission> permissionList = new ArrayList<>();
        for (long permissionId : addPermissionForm.getPermissions()) {
            Permission permission = permissionRepository.findById(permissionId).orElse(null);
            if (permission != null) {
                permissionList.add(permission);
            }
        }
        group.getPermissions().removeIf(permission -> permissionList.stream().anyMatch(p -> p.getId().equals(permission.getId())));
        groupRepository.save(group);
        apiMessageDto.setMessage("Remove permission success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<GroupDto>> autoCompleteGroup(GroupCriteria groupCriteria, Pageable pageable) {
        Page<Group> groups = groupRepository.findAll(groupCriteria.getSpecification(), pageable);
        ResponseListDto<GroupDto> responseListDto = new ResponseListDto(groupMapper.fromEntityToGroupDtoAutoCompleteList(groups.getContent()), groups.getTotalElements(), groups.getTotalPages());
        ApiMessageDto<ResponseListDto<GroupDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Auto complete group success.");
        return apiMessageDto;
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('GR_D')")
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed delete!");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            throw new NotFoundException("Group not found!", ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        groupRepository.delete(group);
        apiMessageDto.setMessage("Delete group success.");
        return apiMessageDto;
    }
}
