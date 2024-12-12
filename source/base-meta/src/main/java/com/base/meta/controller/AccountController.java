package com.base.meta.controller;


import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.account.ForgetPasswordDto;
import com.base.meta.dto.account.RequestForgetPasswordForm;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.exception.UnauthorizationException;
import com.base.meta.form.account.CreateAccountAdminForm;
import com.base.meta.form.account.ForgetPasswordForm;
import com.base.meta.form.account.UpdateAccountAdminForm;
import com.base.meta.form.account.UpdateProfileAdminForm;
import com.base.meta.mapper.AccountMapper;
import com.base.meta.model.Account;
import com.base.meta.model.Group;
import com.base.meta.model.criteria.AccountCriteria;
import com.base.meta.repository.*;
import com.base.meta.service.BaseMetaApiService;
import com.base.meta.utils.AESUtils;
import com.base.meta.utils.ConvertUtils;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.ApiResponse;
import com.base.meta.dto.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/v1/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AccountController extends ABasicController {
    public static final String PREFIX_ENTITY = "ACC";
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    BaseMetaApiService baseMetaApiService;

    @PostMapping(value = "/create-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_C_AD')")
    @Transactional
    public ApiResponse<String> createAdmin(@Valid @RequestBody CreateAccountAdminForm createAccountAdminForm, BindingResult bindingResult) {
        ApiResponse<String> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findAccountByUsername(createAccountAdminForm.getUsername());
        if (account != null) {
            throw new NotFoundException("Username is existed!", ErrorCode.ACCOUNT_ERROR_USERNAME_EXIST);
        }
        Group group = groupRepository.findById(createAccountAdminForm.getGroupId()).orElse(null);
        if (group == null) {
            throw new BadRequestException("Group not found!", ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        account = new Account();
        account.setUsername(createAccountAdminForm.getUsername());
        account.setPassword(passwordEncoder.encode(createAccountAdminForm.getPassword()));
        account.setFullName(createAccountAdminForm.getFullName());
        account.setKind(BaseMetaConstant.USER_KIND_ADMIN);
        account.setEmail(createAccountAdminForm.getEmail());
        account.setGroup(group);
        account.setFlag(createAccountAdminForm.getFlag());
        account.setPhone(createAccountAdminForm.getPhone());
        if (StringUtils.isNoneBlank(createAccountAdminForm.getAvatarPath())) {
            account.setAvatarPath(createAccountAdminForm.getAvatarPath());
        }
        account.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        accountRepository.save(account);

        apiMessageDto.setMessage("Create an account admin success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update_admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_U_AD')")
    public ApiResponse<String> updateAdmin(@Valid @RequestBody UpdateAccountAdminForm updateAccountAdminForm, BindingResult bindingResult) {
        ApiResponse<String> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findById(updateAccountAdminForm.getId()).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        Group group = groupRepository.findById(updateAccountAdminForm.getGroupId()).orElse(null);
        if (group == null) {
            throw new BadRequestException("Group not found!", ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        if (StringUtils.isNoneBlank(updateAccountAdminForm.getPassword())) {
            account.setPassword(passwordEncoder.encode(updateAccountAdminForm.getPassword()));
        }
        account.setFullName(updateAccountAdminForm.getFullName());
        if (StringUtils.isNoneBlank(updateAccountAdminForm.getAvatarPath())) {
            if (account.getAvatarPath() != null && !updateAccountAdminForm.getAvatarPath().equals(account.getAvatarPath())) {
                //delete old image
                baseMetaApiService.deleteFile(account.getAvatarPath());
            }
            account.setAvatarPath(updateAccountAdminForm.getAvatarPath());
        }
        account.setGroup(group);
        account.setFlag(updateAccountAdminForm.getFlag());
        account.setEmail(updateAccountAdminForm.getEmail());
        account.setPhone(updateAccountAdminForm.getPhone());
        accountRepository.save(account);
        apiMessageDto.setMessage("Update account admin success.");
        return apiMessageDto;

    }


    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_V_AD')")
    public ApiResponse<Account> getAccount(@PathVariable("id") Long id) {
        ApiResponse<Account> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(account);
        apiMessageDto.setMessage("Get account success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_D_AD')")
    public ApiResponse<String> deleteAccount(@PathVariable("id") Long id) {
        ApiResponse<String> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (account.getIsSuperAdmin()) {
            throw new BadRequestException("Not allow delete super admin!", ErrorCode.ACCOUNT_ERROR_NOT_ALLOW_DELETE_SUPPER_ADMIN);
        }
        //delete avatar file
        baseMetaApiService.deleteFile(account.getAvatarPath());
        accountRepository.deleteById(id);
        apiMessageDto.setMessage("Delete account success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<AccountDto> profile() {
        long id = getCurrentUser();
        Account account = accountRepository.findById(id).orElse(null);
        ApiResponse<AccountDto> apiMessageDto = new ApiResponse<>();
        if (account == null) {
            throw new BadRequestException("Account not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(accountMapper.fromAccountToDto(account));
        apiMessageDto.setMessage("Get Account success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-profile-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> updateProfileAdmin(final HttpServletRequest request, @Valid @RequestBody UpdateProfileAdminForm updateProfileAdminForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed update!");
        }

        ApiResponse<String> apiMessageDto = new ApiResponse<>();
        long id = getCurrentUser();
        var account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        if (!passwordEncoder.matches(updateProfileAdminForm.getOldPassword(), account.getPassword())) {
            throw new BadRequestException("Old password is wrong!", ErrorCode.ACCOUNT_ERROR_WRONG_PASSWORD);
        }

        if (StringUtils.isNoneBlank(updateProfileAdminForm.getPassword())) {
            account.setPassword(passwordEncoder.encode(updateProfileAdminForm.getPassword()));
        }
        account.setPhone(updateProfileAdminForm.getPhone());
        account.setFullName(updateProfileAdminForm.getFullName());
        account.setAvatarPath(updateProfileAdminForm.getAvatarPath());
        accountRepository.save(account);

        apiMessageDto.setMessage("Update admin account success");
        return apiMessageDto;

    }

    @PostMapping(value = "/request-forget-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> requestForgetPassword(@Valid @RequestBody RequestForgetPasswordForm forgetForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(forgetForm.getEmail()).orElseThrow(
                () -> new NotFoundException("Email not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND));

        String otp = baseMetaApiService.getOTPForgetPassword();
        account.setAttemptCode(0);
        account.setResetPwdCode(otp);
        account.setResetPwdTime(new Date());
        accountRepository.save(account);

        //send email
        baseMetaApiService.sendOTPEmail(account.getEmail(), otp, 2);
        String hash = AESUtils.encrypt(account.getId() + ";" + otp, true);

        apiMessageDto.setResult(true);
        apiMessageDto.setData(hash);
        apiMessageDto.setMessage("Request forget password successfully, please check email!");
        return apiMessageDto;
    }

    @PostMapping(value = "/forget-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Long> forgetPassword(@Valid @RequestBody ForgetPasswordForm forgetForm, BindingResult bindingResult) {
        ApiResponse<Long> apiMessageDto = new ApiResponse<>();

        log.info("Forget password: {}", forgetForm.getIdHash());
        String[] hash = AESUtils.decrypt(forgetForm.getIdHash(), true).split(";", 2);
        Long id = ConvertUtils.convertStringToLong(hash[0]);
        if (id <= 0) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_WRONG_HASH_RESET_PASS);
            return apiMessageDto;
        }


        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }

        if (account.getAttemptCode() >= BaseMetaConstant.MAX_ATTEMPT_FORGET_PWD) {
            throw new BadRequestException("Account is locked!", ErrorCode.ACCOUNT_ERROR_LOCKED);
        }

        if (!account.getResetPwdCode().equals(forgetForm.getOtp())
                || (new Date().getTime() - account.getResetPwdTime().getTime() >= BaseMetaConstant.MAX_TIME_FORGET_PWD)) {
            //tang so lan
            account.setAttemptCode(account.getAttemptCode() + 1);
            accountRepository.save(account);
            throw new BadRequestException("OTP is wrong or expired!", ErrorCode.ACCOUNT_ERROR_OTP_INVALID);
        }
        account.setResetPwdTime(null);
        account.setResetPwdCode(null);
        account.setAttemptCode(null);
        account.setPassword(passwordEncoder.encode(forgetForm.getNewPassword()));
        accountRepository.save(account);
        apiMessageDto.setMessage("Change password success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_L_AD')")
    public ApiResponse<ResponseListDto<AccountDto>> listAccount(AccountCriteria accountCriteria, Pageable pageable) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed view!");
        }
        ApiResponse<ResponseListDto<AccountDto>> apiMessageDto = new ApiResponse<>();
        Page<Account> page = accountRepository.findAll(accountCriteria.getSpecification(), pageable);
        ResponseListDto<AccountDto> responseListDto = new ResponseListDto(accountMapper.fromEntityToAccountDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List account success.");
        return apiMessageDto;
    }
}
