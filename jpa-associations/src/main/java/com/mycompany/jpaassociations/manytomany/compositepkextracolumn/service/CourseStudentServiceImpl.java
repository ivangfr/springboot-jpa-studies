package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.service;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.exception.CourseStudentNotFoundException;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudent;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudentPk;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository.CourseStudentRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseStudentServiceImpl implements CourseStudentService {

    private final CourseStudentRepository courseStudentRepository;

    public CourseStudentServiceImpl(CourseStudentRepository courseStudentRepository) {
        this.courseStudentRepository = courseStudentRepository;
    }

    @Override
    public CourseStudent validateAndGetCourseStudent(Long courseId, Long studentId) {
        CourseStudentPk courseStudentPk = new CourseStudentPk(courseId, studentId);
        return courseStudentRepository.findById(courseStudentPk)
                .orElseThrow(() -> new CourseStudentNotFoundException(String.format("CourseStudent with id '%s' not found", courseStudentPk)));
    }

    @Override
    public CourseStudent saveCourseStudent(CourseStudent courseStudent) {
        return courseStudentRepository.save(courseStudent);
    }

    @Override
    public void deleteCourseStudent(CourseStudent courseStudent) {
        courseStudentRepository.delete(courseStudent);
    }
}