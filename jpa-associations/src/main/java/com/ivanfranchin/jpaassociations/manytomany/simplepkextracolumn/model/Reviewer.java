package com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.model;

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
@ToString(exclude = "comments")
@EqualsAndHashCode(exclude = "comments")
@Entity
@Table(name = "reviewers")
public class Reviewer {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private Set<Comment> comments = new LinkedHashSet<>();

    @Column(nullable = false)
    private String name;
}
