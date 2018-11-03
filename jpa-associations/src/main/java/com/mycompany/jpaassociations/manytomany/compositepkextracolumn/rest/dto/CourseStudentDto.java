package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CourseStudentDto {

    private Course course;
    private Student student;
    private Date registrationDate;
    private Short grade;

    @Data
    public static final class Course {
        private Long id;
        private String name;
    }

    @Data
    public static final class Student {
        private Long id;
        private String name;
    }

}
