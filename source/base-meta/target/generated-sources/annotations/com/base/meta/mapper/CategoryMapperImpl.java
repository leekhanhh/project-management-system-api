package com.base.meta.mapper;

import com.base.meta.dto.category.CategoryDto;
import com.base.meta.form.category.CreateCategoryForm;
import com.base.meta.form.category.UpdateCategoryForm;
import com.base.meta.model.Category;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-07T17:54:45+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category fromCreateCategoryFormToEntity(CreateCategoryForm createCategoryForm) {
        if ( createCategoryForm == null ) {
            return null;
        }

        Category category = new Category();

        category.setCode( createCategoryForm.getCategoryCode() );
        category.setOrdering( createCategoryForm.getCategoryOrdering() );
        category.setKind( createCategoryForm.getCategoryKind() );
        category.setDescription( createCategoryForm.getCategoryDescription() );
        category.setName( createCategoryForm.getCategoryName() );

        return category;
    }

    @Override
    public void fromUpdateCategoryFormToEntity(UpdateCategoryForm updateCategoryForm, Category category) {
        if ( updateCategoryForm == null ) {
            return;
        }

        if ( updateCategoryForm.getFlag() != null ) {
            category.setFlag( updateCategoryForm.getFlag() );
        }
        if ( updateCategoryForm.getCategoryOrdering() != null ) {
            category.setOrdering( updateCategoryForm.getCategoryOrdering() );
        }
        if ( updateCategoryForm.getCategoryDescription() != null ) {
            category.setDescription( updateCategoryForm.getCategoryDescription() );
        }
    }

    @Override
    public CategoryDto fromEntityToAdminDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryKind( category.getKind() );
        categoryDto.setFlag( category.getFlag() );
        if ( category.getCreatedDate() != null ) {
            categoryDto.setCreatedDate( LocalDateTime.ofInstant( category.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( category.getModifiedDate() != null ) {
            categoryDto.setModifiedDate( LocalDateTime.ofInstant( category.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        categoryDto.setId( category.getId() );
        categoryDto.setCategoryCode( category.getCode() );
        categoryDto.setCategoryOrdering( category.getOrdering() );
        categoryDto.setDisplayId( category.getDisplayId() );
        categoryDto.setCategoryName( category.getName() );
        categoryDto.setParentId( categoryParentCategoryId( category ) );
        categoryDto.setCategoryDescription( category.getDescription() );

        return categoryDto;
    }

    @Override
    public List<CategoryDto> fromEntityListToCategoryDtoList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( categories.size() );
        for ( Category category : categories ) {
            list.add( fromEntityToAdminDto( category ) );
        }

        return list;
    }

    @Override
    public CategoryDto fromEntityToAdminDtoAutoComplete(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryKind( category.getKind() );
        categoryDto.setId( category.getId() );
        categoryDto.setCategoryCode( category.getCode() );
        categoryDto.setCategoryOrdering( category.getOrdering() );
        categoryDto.setCategoryName( category.getName() );
        categoryDto.setParentId( categoryParentCategoryId( category ) );
        categoryDto.setCategoryDescription( category.getDescription() );

        return categoryDto;
    }

    @Override
    public List<CategoryDto> fromEntityListToCategoryDtoAutoComplete(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( categories.size() );
        for ( Category category : categories ) {
            list.add( fromEntityToAdminDtoAutoComplete( category ) );
        }

        return list;
    }

    @Override
    public CategoryDto fromEntityToShortDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryKind( category.getKind() );
        categoryDto.setId( category.getId() );
        categoryDto.setCategoryCode( category.getCode() );
        categoryDto.setCategoryName( category.getName() );
        categoryDto.setCategoryDescription( category.getDescription() );

        return categoryDto;
    }

    @Override
    public List<CategoryDto> fromEntityToShortDtoList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( categories.size() );
        for ( Category category : categories ) {
            list.add( fromEntityToShortDto( category ) );
        }

        return list;
    }

    @Override
    public CategoryDto fromEntityToAutoCompleteNameToDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId( category.getId() );
        categoryDto.setCategoryName( category.getName() );

        return categoryDto;
    }

    @Override
    public List<CategoryDto> fromEntityToAutoCompleteNameToDtoList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( categories.size() );
        for ( Category category : categories ) {
            list.add( fromEntityToAutoCompleteNameToDto( category ) );
        }

        return list;
    }

    private Long categoryParentCategoryId(Category category) {
        if ( category == null ) {
            return null;
        }
        Category parentCategory = category.getParentCategory();
        if ( parentCategory == null ) {
            return null;
        }
        Long id = parentCategory.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
