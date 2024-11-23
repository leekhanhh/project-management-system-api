package com.base.meta.controller;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.testdefectcomment.TestDefectCommentDto;
import com.base.meta.exception.NotFoundException;
import com.base.meta.form.testdefectcomment.CreateTestDefectCommentForm;
import com.base.meta.form.testdefectcomment.UpdateTestDefectCommentForm;
import com.base.meta.mapper.TestDefectCommentMapper;
import com.base.meta.model.Account;
import com.base.meta.model.TestDefect;
import com.base.meta.model.TestDefectComment;
import com.base.meta.model.criteria.TestDefectCommentCriteria;
import com.base.meta.repository.AccountRepository;
import com.base.meta.repository.TestDefectCommentRepository;
import com.base.meta.repository.TestDefectRepository;
import com.base.meta.service.NotificationService;
import com.base.meta.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/test-defect-comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TestDefectCommentController extends ABasicController{
    @Autowired
    TestDefectCommentRepository testDefectCommentRepository;
    @Autowired
    TestDefectCommentMapper testDefectCommentMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TestDefectRepository testDefectRepository;
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedisService redisService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    NotificationService notificationService;


    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> createTestDefectComment(@Valid @RequestBody CreateTestDefectCommentForm createTestDefectCommentForm, BindingResult bindingResult) throws ExecutionException, InterruptedException {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        TestDefect testDefect = testDefectRepository.findFirstById(createTestDefectCommentForm.getTestDefectId()).orElseThrow(
                () -> new NotFoundException("Test Defect not found!", ErrorCode.TEST_DEFECT_ERROR_NOT_EXIST)
        );
        Account account = accountRepository.findFirstById(createTestDefectCommentForm.getSenderId());
        if (account == null) {
            throw new NotFoundException("Sender not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        createTestDefectCommentForm.setSenderToken(getCurrentToken());
        TestDefectComment testDefectComment = testDefectCommentMapper.fromCreateTestDefectCommentFormToEntity(createTestDefectCommentForm);
        testDefectComment.setTestDefect(testDefect);
        testDefectComment.setSender(account);
        testDefectCommentRepository.save(testDefectComment);

        messagingTemplate.convertAndSend("/topic/test-defect-comment", testDefectComment.getComment());
        String message = "New comment from " + account.getUsername() + " on test defect " + testDefect.getName();
        rabbitTemplate.convertAndSend("commentQueue", message);
        redisService.saveMessageToRedis(message);
        redisTemplate.opsForList().leftPush("comment_messages", message);
        log.info("Sending message to queue: {}", message);
        //messagingTemplate.convertAndSendToUser("/topic/messages", account.getUsername(), createTestDefectCommentForm.getComment());
        apiMessageDto.setMessage("Create test defect comment successfully!");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> updateTestDefectComment(@Valid @RequestBody UpdateTestDefectCommentForm updateTestDefectCommentForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        TestDefectComment testDefectComment = testDefectCommentRepository.findById(updateTestDefectCommentForm.getId()).orElseThrow(
                () -> new NotFoundException("Test Defect Comment not found!", ErrorCode.TEST_DEFECT_COMMENT_ERROR_NOT_EXIST)
        );
        testDefectCommentMapper.fromUpdateTestDefectCommentFormToEntity(updateTestDefectCommentForm, testDefectComment);
        testDefectCommentRepository.save(testDefectComment);
        apiMessageDto.setMessage("Update test defect comment successfully!");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> deleteTestDefectComment(@PathVariable Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        TestDefectComment testDefectComment = testDefectCommentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test Defect Comment not found!", ErrorCode.TEST_DEFECT_COMMENT_ERROR_NOT_EXIST)
        );
        testDefectCommentRepository.delete(testDefectComment);
        apiMessageDto.setMessage("Delete test defect comment successfully!");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TestDefectCommentDto> getTestDefectComment(@PathVariable Long id) {
        ApiMessageDto<TestDefectCommentDto> apiMessageDto = new ApiMessageDto<>();
        TestDefectComment testDefectComment = testDefectCommentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test Defect Comment not found!", ErrorCode.TEST_DEFECT_COMMENT_ERROR_NOT_EXIST)
        );
        apiMessageDto.setData(testDefectCommentMapper.fromEntityToDto(testDefectComment));
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<TestDefectCommentDto>> listTestDefectComment(TestDefectCommentCriteria testDefectCommentCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<TestDefectCommentDto>> apiMessageDto = new ApiMessageDto<>();
        Page<TestDefectComment> testDefectCommentPage = testDefectCommentRepository.findAll(testDefectCommentCriteria.getSpecification(), pageable);
        ResponseListDto<TestDefectCommentDto> responseListDto = new ResponseListDto(testDefectCommentMapper.fromEntityToDtoList(testDefectCommentPage.getContent()), testDefectCommentPage.getTotalElements(), testDefectCommentPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }



}
