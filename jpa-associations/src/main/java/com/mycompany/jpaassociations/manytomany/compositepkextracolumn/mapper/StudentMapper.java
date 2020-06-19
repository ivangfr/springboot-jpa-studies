package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.mapper;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Student;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CreateStudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.StudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateStudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StudentMapper {

    Student toStudent(CreateStudentDto createStudentDto);

    StudentDto toStudentDto(Student student);

    void updateStudentFromDto(UpdateStudentDto updateStudentDto, @MappingTarget Student student);

}
