package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class StudentResponse {

    Long id;
    String name;
    List<CourseStudent> courses;

    @Value
    public static class CourseStudent {
        Course course;
        Date registrationDate;
        Short grade;

        @Value
        public static class Course {
            Long id;
            String name;
        }
    }
}
