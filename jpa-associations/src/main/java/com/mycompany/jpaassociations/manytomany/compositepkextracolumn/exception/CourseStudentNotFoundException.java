package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseStudentNotFoundException extends RuntimeException {

    public CourseStudentNotFoundException(String message) {
        super(message);
    }
}
