package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.service;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudent;

public interface CourseStudentService {

    CourseStudent validateAndGetCourseStudent(Long courseId, Long studentId);

    CourseStudent saveCourseStudent(CourseStudent courseStudent);

    void deleteCourseStudent(CourseStudent courseStudent);

}
