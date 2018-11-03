package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudent;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudentPk;
import org.springframework.data.repository.CrudRepository;

public interface CourseStudentRepository extends CrudRepository<CourseStudent, CourseStudentPk> {
}
