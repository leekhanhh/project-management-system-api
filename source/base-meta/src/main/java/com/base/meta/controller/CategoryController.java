package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.dto.ApiMessageDto;
import com.base.meta.dto.ErrorCode;
import com.base.meta.dto.ResponseListDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.form.category.CreateCategoryForm;
import com.base.meta.form.category.UpdateCategoryForm;
import com.base.meta.mapper.CategoryMapper;
import com.base.meta.model.Category;
import com.base.meta.model.criteria.CategoryCriteria;
import com.base.meta.repository.CategoryRepository;
import com.base.meta.service.BaseMetaApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CategoryController extends ABasicController{
    private static final String PREFIX_ENTITY = "CATE";
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    BaseMetaApiService baseMetaApiService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CATE_L')")
    public ApiMessageDto<ResponseListDto<CategoryDto>> listCategory(CategoryCriteria categoryCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CategoryDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Category> listCategory = categoryRepository.findAll(categoryCriteria.getSpecification(), pageable);
        ResponseListDto<CategoryDto> responseListObj = new ResponseListDto(categoryMapper.fromEntityListToCategoryDtoList(listCategory.getContent()), listCategory.getTotalElements(), listCategory.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("Get list category success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<CategoryDto>> autoCompleteListCategory(CategoryCriteria categoryCriteria, @PageableDefault(size = 10) Pageable pageable) {
        ApiMessageDto<ResponseListDto<CategoryDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Category> listCategory = categoryRepository.findAll(categoryCriteria.getSpecification(), pageable);
        ResponseListDto<CategoryDto> responseListObj = new ResponseListDto(categoryMapper.fromEntityListToCategoryDtoAutoComplete(listCategory.getContent()), listCategory.getTotalElements(), listCategory.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("Get auto-complete list category success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CATE_V')")
    public ApiMessageDto<CategoryDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<CategoryDto> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new NotFoundException("[Category] Category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(categoryMapper.fromEntityToAdminDto(category));
        apiMessageDto.setMessage("Get a category success.");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CATE_C')")
    @Transactional
    public ApiMessageDto<Long> createCategory(@Valid @RequestBody CreateCategoryForm createCategoryForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findByNameAndKind(createCategoryForm.getCategoryName(), createCategoryForm.getCategoryKind());
        if (category != null) {
            throw new BadRequestException("[Category] Category name exist in kind!", ErrorCode.CATEGORY_ERROR_NAME_EXIST_IN_KIND);
        }
        category = categoryMapper.fromCreateCategoryFormToEntity(createCategoryForm);
        if (createCategoryForm.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(createCategoryForm.getParentId()).orElse(null);
            if (parentCategory == null || parentCategory.getParentCategory() != null) {
                throw new BadRequestException("[Category] Not found parent category or parent category is child of other category!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
            }
            category.setParentCategory(parentCategory);
        }
        Category checkCode = categoryRepository.findFirstByCode(createCategoryForm.getCategoryCode());
        if (checkCode != null && checkCode.getParentCategory() == null && !StringUtils.equals("", createCategoryForm.getCategoryCode())) {
            throw new BadRequestException("[Category] Code exist in kind!", ErrorCode.CATEGORY_ERROR_CODE_EXIST);
        }
        if(Boolean.TRUE.equals(categoryRepository.existsByKind(createCategoryForm.getCategoryKind()))){
            throw new BadRequestException("[Category] Kind exist!", ErrorCode.CATEGORY_ERROR_KIND_EXIST);
        }
        category.setDisplayId(baseMetaApiService.generateDisplayId(PREFIX_ENTITY, new Date()));
        categoryRepository.save(category);
        apiMessageDto.setData(category.getId());
        apiMessageDto.setMessage("Create a new category success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CATE_U')")
    @Transactional
    public ApiMessageDto<Long> updateCategory(@Valid @RequestBody UpdateCategoryForm updateCategoryForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findById(updateCategoryForm.getId()).orElse(null);
        if (category == null) {
            throw new NotFoundException("[Category] Category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category checkName = categoryRepository.findByNameAndKind(updateCategoryForm.getCategoryName(), category.getKind());
        if (checkName == null && !Objects.equals(category.getName(), updateCategoryForm.getCategoryName().trim())) {
            category.setName(updateCategoryForm.getCategoryName().trim());
        }
        categoryMapper.fromUpdateCategoryFormToEntity(updateCategoryForm, category);
        categoryRepository.save(category);
        apiMessageDto.setData(category.getId());
        apiMessageDto.setMessage("Update a category success.");
        return apiMessageDto;
    }

    @PreAuthorize("hasRole('CATE_D')")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> deleteCategory(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new NotFoundException("[Category] Category not found!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
        apiMessageDto.setData(id);
        apiMessageDto.setMessage("Delete a category success.");
        return apiMessageDto;
    }
}
