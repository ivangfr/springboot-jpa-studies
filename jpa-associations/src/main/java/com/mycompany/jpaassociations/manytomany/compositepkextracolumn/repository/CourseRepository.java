package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
}
