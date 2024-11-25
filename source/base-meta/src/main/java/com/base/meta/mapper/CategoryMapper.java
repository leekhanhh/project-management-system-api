package com.base.meta.mapper;

import com.base.meta.dto.category.CategoryDto;
import com.base.meta.form.category.CreateCategoryForm;
import com.base.meta.form.category.UpdateCategoryForm;
import com.base.meta.model.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    @Mapping(source = "categoryName", target = "name")
    @Mapping(source = "categoryDescription", target = "description")
    @Mapping(source = "categoryCode", target = "code")
    @Mapping(source = "categoryOrdering", target = "ordering")
    @Mapping(source = "categoryKind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Category fromCreateCategoryFormToEntity(CreateCategoryForm createCategoryForm);


    @Mapping(source = "categoryDescription", target = "description")
    @Mapping(source = "categoryOrdering", target = "ordering")
    @Mapping(source = "flag", target = "flag")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateCategoryFormToEntity(UpdateCategoryForm updateCategoryForm, @MappingTarget Category category);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(source = "description", target = "categoryDescription")
    @Mapping(source = "code", target = "categoryCode")
    @Mapping(source = "ordering", target = "categoryOrdering")
    @Mapping(source = "kind", target = "categoryKind")
    @Mapping(source = "parentCategory.id", target = "parentId")
    @Mapping(source = "flag", target = "flag")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    CategoryDto fromEntityToAdminDto(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "adminGetMapping")
    List<CategoryDto> fromEntityListToCategoryDtoList(List<Category> categories);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "categoryKind")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(source = "code", target = "categoryCode")
    @Mapping(source = "description", target = "categoryDescription")
    @Mapping(source = "ordering", target = "categoryOrdering")
    @Mapping(source = "parentCategory.id", target = "parentId")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminAutoCompleteMapping")
    CategoryDto fromEntityToAdminDtoAutoComplete(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "adminAutoCompleteMapping")
    List<CategoryDto> fromEntityListToCategoryDtoAutoComplete(List<Category> categories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "categoryKind")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(source = "code", target = "categoryCode")
    @Mapping(source = "description", target = "categoryDescription")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToShortDto")
    CategoryDto fromEntityToShortDto(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "fromEntityToShortDto")
    @Named("fromEntityListToShortDtoList")
    List<CategoryDto> fromEntityToShortDtoList(List<Category> categories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "categoryName")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAutoCompleteNameToDto")
    CategoryDto fromEntityToAutoCompleteNameToDto(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    List<CategoryDto> fromEntityToAutoCompleteNameToDtoList(List<Category> categories);
}
