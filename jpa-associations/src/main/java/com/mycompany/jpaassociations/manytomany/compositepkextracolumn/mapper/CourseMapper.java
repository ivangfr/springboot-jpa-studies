package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.mapper;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Course;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CourseDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CreateCourseDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateCourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CourseMapper {

    Course toCourse(CreateCourseDto createCourseDto);

    CourseDto toCourseDto(Course course);

    void updateCourseFromDto(UpdateCourseDto updateCourseDto, @MappingTarget Course course);

}
