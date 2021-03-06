package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model;

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
