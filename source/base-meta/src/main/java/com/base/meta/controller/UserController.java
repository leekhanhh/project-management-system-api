package com.base.meta.controller;

import com.amazonaws.services.mq.model.UnauthorizedException;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.account.user.UserDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.form.ModifyFlagForm;
import com.base.meta.form.user.UpdateUserForm;
import com.base.meta.form.user.UpdateUserProfileForm;
import com.base.meta.form.user.CreateUserForm;
import com.base.meta.mapper.AccountMapper;
import com.base.meta.mapper.UserMapper;
import com.base.meta.model.*;
import com.base.meta.model.criteria.UserCriteria;
import com.base.meta.repository.*;
import com.base.meta.service.BaseMetaApiService;
import com.base.meta.utils.RandomPasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.Date;

import static com.base.meta.controller.AccountController.PREFIX_ENTITY;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class UserController extends ABasicController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    DataSource dataSource;
    @Autowired
    RandomPasswordUtils randomPasswordUtils;
    @Autowired
    BaseMetaApiService baseMetaApiService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('US_C')")
    public ApiMessageDto<String> createUser(@Valid @RequestBody CreateUserForm createUserForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new BadRequestException("Only super admin can create user!", ErrorCode.USER_ERROR_UNAUTHORIZED);
        }
        Account account = accountRepository.findAccountByUsername(createUserForm.getUsername());
        if (account != null) {
            throw new BadRequestException("Username is existed!", ErrorCode.ACCOUNT_ERROR_USERNAME_EXIST);
        }
        Group group = groupRepository.findFirstByKind(createUserForm.getKind());
        if (group == null) {
            throw new NotFoundException("Group not found! [User kind is invalid!]", ErrorCode.USER_ERROR_GROUP_NOT_FOUND);
        }
        Category status = categoryRepository.findById(createUserForm.getMemberStatusCategoryId()).orElseThrow(()
                -> new NotFoundException("Member status category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        Category position = categoryRepository.findById(createUserForm.getMemberPositionCategoryId()).orElseThrow(()
                -> new NotFoundException("Position category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));
        account = accountMapper.fromCreateUserFormToEntity(createUserForm);
        account.setGroup(group);
        account.setStatus(status);
        account.setPosition(position);
        account.setFullName(createUserForm.getFirstName() + " " + createUserForm.getLastName());
        String password = randomPasswordUtils.createPassword();
        account.setPassword(passwordEncoder.encode(password));
        account.setFlag(1);
        account.setKind(createUserForm.getKind());
        account.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        User user = userMapper.fromCreateUserFormToEntity(createUserForm);
        user.setAccount(account);
        accountRepository.save(account);
        userRepository.save(user);
        baseMetaApiService.sendEmail(account.getEmail(), "This is your password: " + password + "\n Please keep it private!", "Login Project Management Password", false);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Create user success. Password: " + password);
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('US_U')")
    public ApiMessageDto<String> updateUser(@Valid @RequestBody UpdateUserForm updateUserForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new BadRequestException("Only super admin can update user!", ErrorCode.USER_ERROR_UNAUTHORIZED);
        }
        User user = userRepository.findById(updateUserForm.getId()).orElse(null);
        if (user == null) {
            throw new BadRequestException("User not found!", ErrorCode.USER_ERROR_NOT_FOUND);
        }
        Category status = categoryRepository.findById(updateUserForm.getMemberStatusCategoryId()).orElseThrow(()
                -> new NotFoundException("Member status category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        Category position = categoryRepository.findById(updateUserForm.getMemberPositionCategoryId()).orElseThrow(()
                -> new NotFoundException("Position category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND));
        userMapper.updateUserFromEntity(updateUserForm, user);
        user.getAccount().setStatus(status);
        user.getAccount().setPosition(position);
        userRepository.save(user);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Update user success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('US_L')")
    public ApiMessageDto<ResponseListDto<UserDto>> listUser(UserCriteria userCriteria, Pageable pageable) {
        if (!isSuperAdmin() && !isPM()) {
            throw new BadRequestException("Only super admin and PM can list user!", ErrorCode.USER_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<ResponseListDto<UserDto>> apiMessageDto = new ApiMessageDto<>();
        Page<User> userPage = userRepository.findAll(userCriteria.getSpecification(), pageable);
        ResponseListDto<UserDto> responseListDto = new ResponseListDto(userMapper.fromEntityToUserDtoList(userPage.getContent()), userPage.getTotalElements(), userPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get user list success.");
        return apiMessageDto;
    }

//    @GetMapping(value = "/detail/{tenantId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ApiMessageDto<DbConfigDto> detailuser(@PathVariable("tenantId") String tenantId) {
//        ApiMessageDto<DbConfigDto> apiMessageDto = new ApiMessageDto<>();
//        user user = userRepository.findFirstByTenantId(tenantId).orElse(null);
//        if (user == null) {
//            throw new BadRequestException("user not found!", ErrorCode.user_ERROR_NOT_FOUND);
//        }
//        apiMessageDto.setData(dbConfigMapper.fromEntityToDbConfigDto(user.getDbConfig()));
//        apiMessageDto.setMessage("Get user detail success.");
//        return apiMessageDto;
//    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('US_V')")
    public ApiMessageDto<UserDto> getuser(@PathVariable("id") Long id) {
        ApiMessageDto<UserDto> apiMessageDto = new ApiMessageDto<>();
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("user not found!", ErrorCode.USER_ERROR_NOT_FOUND));
        apiMessageDto.setData(userMapper.fromEntityToUserDto(user));
        apiMessageDto.setMessage("Get user success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UserDto> getProfile() {
        long id = getCurrentUser();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("user not found!", ErrorCode.USER_ERROR_NOT_FOUND));
        ApiMessageDto<UserDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(userMapper.fromEntityToUserDto(user));
        apiMessageDto.setMessage("Get user profile success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiMessageDto<String> updateProfile(@Valid @RequestBody UpdateUserProfileForm updateuserProfileForm, BindingResult bindingResult) {
        long id = getCurrentUser();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("user not found!", ErrorCode.USER_ERROR_NOT_FOUND));
        if (!passwordEncoder.matches(updateuserProfileForm.getOldPassword(), user.getAccount().getPassword())) {
            throw new BadRequestException("Old password is incorrect!", ErrorCode.USER_ERROR_WRONG_PASSWORD);
        }
        if (StringUtils.isNoneBlank(updateuserProfileForm.getPassword())) {
            user.getAccount().setPassword(passwordEncoder.encode(updateuserProfileForm.getPassword()));
        }
        if (StringUtils.isNoneBlank(updateuserProfileForm.getAvatarPath())
                && !updateuserProfileForm.getAvatarPath().equals(user.getAccount().getAvatarPath())) {
            //delete old image
            baseMetaApiService.deleteFile(user.getAccount().getAvatarPath());
        }
        userMapper.updateUserProfileFromEntity(updateuserProfileForm, user);
        Account account = user.getAccount();
        account.setFullName(updateuserProfileForm.getFirstName() + " " + updateuserProfileForm.getLastName());
        accountRepository.save(account);
        userRepository.save(user);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Update user profile success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('US_D')")
    public ApiMessageDto<String> deleteUser(@PathVariable("id") Long id) {
        if (!isSuperAdmin()) {
            throw new BadRequestException("Only super admin can delete user!", ErrorCode.USER_ERROR_UNAUTHORIZED);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("user not found!", ErrorCode.USER_ERROR_NOT_FOUND));

        ProjectMember projectMember = projectMemberRepository.findByAccountId(id);
        if (projectMember != null)
        {
            throw new BadRequestException("Can not delete user attending in project!", ErrorCode.USER_ERROR_IN_PROJECT);
        }
        userRepository.delete(user);
        accountRepository.delete(user.getAccount());
        //already delete user mapping in the ProjectMember table in liquibase onDeleteCascade constraint
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Delete user success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-flag", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('US_UF')")
    public ApiMessageDto<String> updateFlagUser(@RequestBody ModifyFlagForm modifyFlagForm) {
        if (!isSuperAdmin()) {
            throw new UnauthorizedException("Only super admin can update user flag!");
        }
        User user = userRepository.findById(modifyFlagForm.getObjectId())
                .orElseThrow(() -> new NotFoundException("user not found!", ErrorCode.USER_ERROR_NOT_FOUND));
        user.getAccount().setFlag(modifyFlagForm.getFlag());
        userRepository.save(user);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Update user flag success.");
        return apiMessageDto;
    }
}
