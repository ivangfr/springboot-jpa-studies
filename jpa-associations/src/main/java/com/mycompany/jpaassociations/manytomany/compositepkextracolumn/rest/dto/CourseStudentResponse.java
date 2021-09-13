package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import lombok.Value;

import java.util.Date;

@Value
public class CourseStudentResponse {

    Course course;
    Student student;
    Date registrationDate;
    Short grade;

    @Value
    public static class Course {
        Long id;
        String name;
    }

    @Value
    public static class Student {
        Long id;
        String name;
    }
}
