package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CourseDto {

    private Long id;
    private String name;
    private List<CourseStudent> students;

    @Data
    public static final class CourseStudent {
        private Student student;
        private Date registrationDate;
        private Short grade;

        @Data
        public static final class Student {
            private Long id;
            private String name;
        }
    }

}
