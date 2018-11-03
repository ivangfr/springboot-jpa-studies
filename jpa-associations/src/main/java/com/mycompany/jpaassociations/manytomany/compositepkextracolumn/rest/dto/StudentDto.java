package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StudentDto {

    private Long id;
    private String name;
    private List<CourseStudent> courses;

    @Data
    public static final class CourseStudent {
        private Course course;
        private Date registrationDate;
        private Short grade;

        @Data
        public static final class Course {
            private Long id;
            private String name;
        }
    }

}
