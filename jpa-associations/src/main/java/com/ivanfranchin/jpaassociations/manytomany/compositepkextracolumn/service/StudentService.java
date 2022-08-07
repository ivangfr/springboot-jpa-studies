package com.ivanfranchin.jpaassociations.manytomany.compositepkextracolumn.service;

import com.ivanfranchin.jpaassociations.manytomany.compositepkextracolumn.model.Student;

public interface StudentService {

    Student validateAndGetStudent(Long id);

    Student saveStudent(Student student);

    void deleteStudent(Student student);
}
