package com.base.meta.mapper;

import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.form.requirement.CreateRequirementForm;
import com.base.meta.form.requirement.UpdateRequirementForm;
import com.base.meta.model.Requirement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-30T12:32:44+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class RequirementMapperImpl implements RequirementMapper {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Requirement fromCreateRequirementFormToEntity(CreateRequirementForm createRequirementForm) {
        if ( createRequirementForm == null ) {
            return null;
        }

        Requirement requirement = new Requirement();

        requirement.setDescription( createRequirementForm.getDescription() );
        requirement.setAcceptance( createRequirementForm.getAcceptance() );

        return requirement;
    }

    @Override
    public RequirementDto fromEntityToRequirementDto(Requirement requirement) {
        if ( requirement == null ) {
            return null;
        }

        RequirementDto requirementDto = new RequirementDto();

        requirementDto.setAcceptance( requirement.getAcceptance() );
        requirementDto.setName( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getName() ) );
        requirementDto.setDescription( requirement.getDescription() );
        requirementDto.setProject( projectMapper.fromEntityToProjectDto( requirement.getProject() ) );
        requirementDto.setDetailClassification( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getDetailClassification() ) );
        requirementDto.setId( requirement.getId() );
        requirementDto.setDevision( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getDevision() ) );

        return requirementDto;
    }

    @Override
    public List<RequirementDto> fromEntityToRequirementDtoList(List<Requirement> requirements) {
        if ( requirements == null ) {
            return null;
        }

        List<RequirementDto> list = new ArrayList<RequirementDto>( requirements.size() );
        for ( Requirement requirement : requirements ) {
            list.add( fromEntityToRequirementDto( requirement ) );
        }

        return list;
    }

    @Override
    public void fromUpdateRequirementFormToEntity(UpdateRequirementForm updateRequirementForm, Requirement requirement) {
        if ( updateRequirementForm == null ) {
            return;
        }

        if ( updateRequirementForm.getDescription() != null ) {
            requirement.setDescription( updateRequirementForm.getDescription() );
        }
        if ( updateRequirementForm.getAcceptance() != null ) {
            requirement.setAcceptance( updateRequirementForm.getAcceptance() );
        }
    }

    @Override
    public RequirementDto fromEntityToAutoCompleteRequirementDto(Requirement requirement) {
        if ( requirement == null ) {
            return null;
        }

        RequirementDto requirementDto = new RequirementDto();

        requirementDto.setAcceptance( requirement.getAcceptance() );
        requirementDto.setName( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getName() ) );
        requirementDto.setDescription( requirement.getDescription() );
        requirementDto.setId( requirement.getId() );

        return requirementDto;
    }
}
