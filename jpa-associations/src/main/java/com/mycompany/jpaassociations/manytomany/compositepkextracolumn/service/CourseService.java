package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.service;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Course;

public interface CourseService {

    Course validateAndGetCourse(Long id);

    Course saveCourse(Course course);

    void deleteCourse(Course course);
}
