package com.ivanfranchin.jpaassociations.manytomany.compositepkextracolumn.repository;

import com.ivanfranchin.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudent;
import com.ivanfranchin.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudentPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseStudentRepository extends CrudRepository<CourseStudent, CourseStudentPk> {
}
