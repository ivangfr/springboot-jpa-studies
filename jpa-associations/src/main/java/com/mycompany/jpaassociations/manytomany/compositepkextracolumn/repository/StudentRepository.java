package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}
