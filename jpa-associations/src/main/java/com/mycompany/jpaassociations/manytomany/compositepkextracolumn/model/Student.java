package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@ToString(exclude = "courses")
@EqualsAndHashCode(exclude = "courses")
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<CourseStudent> courses = new LinkedHashSet<>();

    @Column(nullable = false)
    private String name;
}
