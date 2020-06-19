package com.mycompany.jpaassociations.manytomany.simplerelationship.mapper;

import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Writer;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.CreateWriterDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateWriterDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.WriterDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WriterMapper {

    Writer toWriter(CreateWriterDto createWriterDto);

    WriterDto toWriterDto(Writer writer);

    void updateWriterFromDto(UpdateWriterDto updateWriterDto, @MappingTarget Writer writer);

}
