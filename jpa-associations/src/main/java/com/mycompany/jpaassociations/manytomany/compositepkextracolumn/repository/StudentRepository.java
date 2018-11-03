package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
