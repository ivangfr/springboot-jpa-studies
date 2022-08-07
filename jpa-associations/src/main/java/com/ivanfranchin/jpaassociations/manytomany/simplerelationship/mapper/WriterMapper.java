package com.ivanfranchin.jpaassociations.manytomany.simplerelationship.mapper;

import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.model.Writer;
import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.rest.dto.CreateWriterRequest;
import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateWriterRequest;
import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.rest.dto.WriterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WriterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Writer toWriter(CreateWriterRequest createWriterRequest);

    WriterResponse toWriterResponse(Writer writer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    void updateWriterFromRequest(UpdateWriterRequest updateWriterRequest, @MappingTarget Writer writer);
}
