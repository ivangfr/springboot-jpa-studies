package com.ivanfranchin.jpaassociations.manytomany.compositepkextracolumn.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@ToString(exclude = "students")
@EqualsAndHashCode(exclude = "students")
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<CourseStudent> students = new LinkedHashSet<>();

    @Column(nullable = false)
    private String name;
}
