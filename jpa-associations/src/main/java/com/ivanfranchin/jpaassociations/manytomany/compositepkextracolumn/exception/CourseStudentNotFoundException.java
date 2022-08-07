package com.ivanfranchin.jpaassociations.manytomany.compositepkextracolumn.exception;

import com.ivanfranchin.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudentPk;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseStudentNotFoundException extends RuntimeException {

    public CourseStudentNotFoundException(CourseStudentPk courseStudentPk) {
        super(String.format("CourseStudent with id '%s' not found", courseStudentPk));
    }
}
