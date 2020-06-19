package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.mapper;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudent;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CourseStudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateCourseStudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CourseStudentMapper {

    CourseStudentDto toCourseStudentDto(CourseStudent courseStudent);

    void updateCourseStudentFromDto(UpdateCourseStudentDto updateCourseStudentDto, @MappingTarget CourseStudent courseStudent);

}
