package com.base.meta.mapper;

import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.model.Requirement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-07T17:54:45+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class RequirementMapperImpl implements RequirementMapper {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public RequirementDto fromEntityToRequirementDto(Requirement requirement) {
        if ( requirement == null ) {
            return null;
        }

        RequirementDto requirementDto = new RequirementDto();

        requirementDto.setDivision( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getDivision() ) );
        requirementDto.setAcceptance( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getAcceptance() ) );
        requirementDto.setName( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getName() ) );
        requirementDto.setDescription( requirement.getDescription() );
        requirementDto.setProject( projectMapper.fromEntityToProjectDto( requirement.getProject() ) );
        requirementDto.setDetailClassification( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getDetailClassification() ) );
        requirementDto.setId( requirement.getId() );
        requirementDto.setDisplayId( requirement.getDisplayId() );

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
    public RequirementDto fromEntityToAutoCompleteRequirementDto(Requirement requirement) {
        if ( requirement == null ) {
            return null;
        }

        RequirementDto requirementDto = new RequirementDto();

        requirementDto.setAcceptance( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getAcceptance() ) );
        requirementDto.setName( categoryMapper.fromEntityToAutoCompleteNameToDto( requirement.getName() ) );
        requirementDto.setDescription( requirement.getDescription() );
        requirementDto.setId( requirement.getId() );

        return requirementDto;
    }
}
