package com.base.meta.mapper;

import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.dto.requirement.RequirementDto;
import com.base.meta.form.program.CreateProgramForm;
import com.base.meta.form.program.UpdateProgramForm;
import com.base.meta.model.Category;
import com.base.meta.model.Program;
import com.base.meta.model.Requirement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-24T19:17:11+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ProgramMapperImpl implements ProgramMapper {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public Program fromCreateProgramFormToEntity(CreateProgramForm createProgramForm) {
        if ( createProgramForm == null ) {
            return null;
        }

        Program program = new Program();

        program.setEndDate( createProgramForm.getEndDate() );
        program.setDescription( createProgramForm.getDescription() );
        program.setProgramCategory( createProgramForm.getProgramCategory() );
        program.setName( createProgramForm.getName() );
        program.setStartDate( createProgramForm.getStartDate() );

        return program;
    }

    @Override
    public ProgramDto fromDtoToEntity(Program program) {
        if ( program == null ) {
            return null;
        }

        ProgramDto programDto = new ProgramDto();

        programDto.setOwner( accountMapper.fromAccountToDto( program.getProgramOwner() ) );
        programDto.setProgramType( categoryMapper.fromEntityToAutoCompleteNameToDto( program.getProgramType() ) );
        if ( program.getEndDate() != null ) {
            programDto.setEndDate( new SimpleDateFormat().format( program.getEndDate() ) );
        }
        programDto.setTester( accountMapper.fromAccountToDto( program.getAssignedTester() ) );
        programDto.setProject( projectMapper.fromEntityToProjectDto( program.getProject() ) );
        programDto.setDescription( program.getDescription() );
        programDto.setRequirement( requirementToRequirementDto( program.getRequirement() ) );
        programDto.setProgramCategory( program.getProgramCategory() );
        programDto.setName( program.getName() );
        programDto.setDeveloper( accountMapper.fromAccountToDto( program.getAssignedDeveloper() ) );
        programDto.setId( program.getId() );
        if ( program.getStartDate() != null ) {
            programDto.setStartDate( new SimpleDateFormat().format( program.getStartDate() ) );
        }
        programDto.setProgramStatus( categoryMapper.fromEntityToAutoCompleteNameToDto( program.getProgramStatus() ) );

        return programDto;
    }

    @Override
    public List<ProgramDto> fromEntityToProgramDtoList(List<Program> programs) {
        if ( programs == null ) {
            return null;
        }

        List<ProgramDto> list = new ArrayList<ProgramDto>( programs.size() );
        for ( Program program : programs ) {
            list.add( fromDtoToEntity( program ) );
        }

        return list;
    }

    @Override
    public void fromUpdateFormToEntity(UpdateProgramForm updateProgramForm, Program program) {
        if ( updateProgramForm == null ) {
            return;
        }

        if ( updateProgramForm.getEndDate() != null ) {
            program.setEndDate( updateProgramForm.getEndDate() );
        }
        if ( updateProgramForm.getDescription() != null ) {
            program.setDescription( updateProgramForm.getDescription() );
        }
        if ( updateProgramForm.getProgramCategory() != null ) {
            program.setProgramCategory( updateProgramForm.getProgramCategory() );
        }
        if ( updateProgramForm.getName() != null ) {
            program.setName( updateProgramForm.getName() );
        }
        if ( updateProgramForm.getStartDate() != null ) {
            program.setStartDate( updateProgramForm.getStartDate() );
        }
    }

    @Override
    public ProgramDto fromEntityToAutoCompleteProgramDto(Program program) {
        if ( program == null ) {
            return null;
        }

        ProgramDto programDto = new ProgramDto();

        if ( program.getEndDate() != null ) {
            programDto.setEndDate( new SimpleDateFormat().format( program.getEndDate() ) );
        }
        programDto.setProject( projectMapper.fromEntityToProjectDto( program.getProject() ) );
        programDto.setName( program.getName() );
        programDto.setId( program.getId() );
        if ( program.getStartDate() != null ) {
            programDto.setStartDate( new SimpleDateFormat().format( program.getStartDate() ) );
        }

        return programDto;
    }

    protected CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setFlag( category.getFlag() );
        if ( category.getModifiedDate() != null ) {
            categoryDto.setModifiedDate( LocalDateTime.ofInstant( category.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( category.getCreatedDate() != null ) {
            categoryDto.setCreatedDate( LocalDateTime.ofInstant( category.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        categoryDto.setId( category.getId() );

        return categoryDto;
    }

    protected RequirementDto requirementToRequirementDto(Requirement requirement) {
        if ( requirement == null ) {
            return null;
        }

        RequirementDto requirementDto = new RequirementDto();

        requirementDto.setFlag( requirement.getFlag() );
        if ( requirement.getModifiedDate() != null ) {
            requirementDto.setModifiedDate( LocalDateTime.ofInstant( requirement.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( requirement.getCreatedDate() != null ) {
            requirementDto.setCreatedDate( LocalDateTime.ofInstant( requirement.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( requirement.getId() != null ) {
            requirementDto.setId( Long.parseLong( requirement.getId() ) );
        }
        requirementDto.setName( requirement.getName() );
        requirementDto.setDescription( requirement.getDescription() );
        requirementDto.setDevision( categoryToCategoryDto( requirement.getDevision() ) );
        requirementDto.setDetailClassification( categoryToCategoryDto( requirement.getDetailClassification() ) );
        requirementDto.setAcceptance( categoryToCategoryDto( requirement.getAcceptance() ) );
        requirementDto.setProject( projectMapper.fromEntityToProjectDto( requirement.getProject() ) );

        return requirementDto;
    }
}