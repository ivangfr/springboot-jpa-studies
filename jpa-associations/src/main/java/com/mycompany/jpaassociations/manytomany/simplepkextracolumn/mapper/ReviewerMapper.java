package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.mapper;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Reviewer;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateReviewerDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.ReviewerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewerMapper {

    Reviewer toReviewer(CreateReviewerDto createReviewerDto);

    ReviewerDto toReviewerDto(Reviewer reviewer);

}
