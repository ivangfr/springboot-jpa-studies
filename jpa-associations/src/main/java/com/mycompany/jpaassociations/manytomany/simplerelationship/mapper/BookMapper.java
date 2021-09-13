package com.mycompany.jpaassociations.manytomany.simplerelationship.mapper;

import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Book;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.BookResponse;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.CreateBookRequest;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateBookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "writers", ignore = true)
    Book toBook(CreateBookRequest createBookRequest);

    BookResponse toBookResponse(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "writers", ignore = true)
    void updateBookFromRequest(UpdateBookRequest updateBookRequest, @MappingTarget Book book);
}
