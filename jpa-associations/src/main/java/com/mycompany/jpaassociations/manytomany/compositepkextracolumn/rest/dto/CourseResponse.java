package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class CourseResponse {

    Long id;
    String name;
    List<CourseStudent> students;

    @Value
    public static class CourseStudent {
        Student student;
        Date registrationDate;
        Short grade;

        @Value
        public static class Student {
            Long id;
            String name;
        }
    }
}
