package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class CourseStudentPk implements Serializable {

    private Long course;
    private Long student;

}
