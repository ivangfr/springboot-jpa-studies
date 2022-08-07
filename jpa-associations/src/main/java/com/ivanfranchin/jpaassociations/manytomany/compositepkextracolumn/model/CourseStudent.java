package com.ivanfranchin.jpaassociations.manytomany.compositepkextracolumn.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Data
@ToString(exclude = {"course", "student"})
@EqualsAndHashCode(exclude = {"course", "student"})
@Entity
@Table(name = "courses_students")
@IdClass(CourseStudentPk.class)
public class CourseStudent {

    @Id
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false)
    private ZonedDateTime registrationDate = ZonedDateTime.now();

    private Short grade;
}
