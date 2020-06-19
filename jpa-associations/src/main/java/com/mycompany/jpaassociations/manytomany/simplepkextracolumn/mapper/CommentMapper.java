package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.mapper;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Comment;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CommentDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateCommentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toComment(CreateCommentDto createCommentDto);

    CommentDto toCommentDto(Comment comment);

}
